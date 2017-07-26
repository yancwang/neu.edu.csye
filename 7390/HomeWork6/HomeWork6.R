library(xlsx)
train <- read.csv("C:/Users/Yanchen/Downloads/7390/Assignment/MidtermProject/train.csv")

# seperate train into three sub data in discrete, continuous and categorical
na <- names(train)
discrete <- c(na[39], na[48], na[53], na[62], na[70], na[80:127])
train_discrete <- train[discrete]

continuous <- c(na[5], na[9:13], na[16], na[18], na[30], na[35:38])
train_continuous <- train[continuous]

dis <- na%in%c(discrete, continuous, na[1], na[128])
train_categorical <- train[!dis]

#train_categorical
#seperate categorical columns into serveral columns
colname = colnames(train_categorical)
for(i in 1:length(train_categorical)){
  colvalue = sort(unique(train_categorical[, i]))
  catNum = length(unique(train_categorical[, i]))
  
  if(catNum <= 6){
    addcolomn = c(paste(colname[i], c(1:catNum),sep = "."))
    train_categorical[, addcolomn]=0
    for(j in 1:catNum){
      replacelist = which(train_categorical[,i] == colvalue[j]) 
      train_categorical[,addcolomn[j]] <- replace(train_categorical[,addcolomn[j]] ,replacelist, 1 )
    }
  }
  
}
train_categorical[, c(1:60)] <- NULL
#summary(train_categorical)

#train_continuous
#assign values to na in each column
for (i in 1:length(train_continuous)) {
  train_continuous_mean <- mean(train_continuous[i][!is.na(train_continuous[i])])
  train_continuous[i][is.na(train_continuous[i])] <- train_continuous_mean
}
#(train_continuous)

#normalize values
for (i in 1:length(train_continuous)) {
  train_continuous_max <- max(train_continuous[i])
  train_continuous_min <- min(train_continuous[i])
  train_continuous[i] <- (train_continuous_max - train_continuous[i]) / (train_continuous_max - train_continuous_min)
}
#summary(train_continuous)

#train_discrete
#assign mean values to na in each column
for (i in 1:length(train_discrete)) {
  train_discrete_mean <- mean(train_discrete[i][!is.na(train_discrete[i])])
  train_discrete[i][is.na(train_discrete[i])] <- train_discrete_mean
}
#summary(train_discrete)

#normalize values
for (i in 1:5) {
  train_discrete_max <- max(train_discrete[i])
  train_discrete_min <- min(train_discrete[i])
  train_discrete[i] <- (train_discrete_max - train_discrete[i]) / (train_discrete_max - train_discrete_min)
}
#summary(train_discrete)

#merge three sub-dataframe into one data frame
train_categorical$Id <- train$Id
train_continuous$Id <- train$Id
train_discrete$Id <- train$Id

train_merge <- merge(train_categorical, train_discrete, by.x = "Id", by.y = "Id")
train_merge <- merge(train_merge, train_continuous, by.x = "Id", by.y = "Id")
train_merge$Response <- train$Response

#seperate data into two
test = train_merge[1:6000, ]
train = train_merge[6001:nrow(train_merge), ]

#generate formula
f <- paste(names(train)[2:217], collapse = '+')
f <- paste('Response ~', f)
f <- as.formula(f)

#logistic regression
fit <- glm(f, data = train)

summary(fit)

#dimension reduction
summary.coefficients <- summary(fit)
coefficients <- summary.coefficients$coefficients

coefficients.data <- as.data.frame(coefficients)
coefficients.data.name <- gsub("`", "", rownames(coefficients.data))
coefficients.data.name <- coefficients.data.name[coefficients.data$`Pr(>|t|)` < 0.00005]  

#generate formula
f <- paste(coefficients.data.name, collapse = '+')
f <- paste('Response ~', f)
f <- as.formula(f)

#logistic regression
fit <- glm(f, data = train)

summary(fit)

#test$Predict <- round(abs(predict(fit, test)))

train.fit <- train[c(coefficients.data.name, "Response")]

#scale data set
maxs <- apply(train.fit[, 1:44], 2, max)
mins <- apply(train.fit[, 1:44], 2, min)

scaled.train <- as.data.frame(scale(train.fit[, 1:44], center = mins, scale = maxs - mins))
scaled.train$Response <- train$Response - 1

#use smaller dataset
library(caTools)

set.seed(101)
split <- sample.split(scaled.train$Response, SplitRatio = 0.70)

train.split <- subset(scaled.train, split = TRUE)
test <- subset(scaled.train, split = FALSE)

train.split.simple <- train.split[sample(nrow(train.split), 5000), ]

library(neuralnet)

#neural network
nn <- neuralnet(f, train.split.simple, hidden = c(5), stepmax = 1e6)
plot(nn)

#predict
predicted.nn.values <- compute(nn, test[1:44])
predicted.nn.values$net.result <- sapply(predicted.nn.values$net.result, round, digits = 0)

table(test$Response, predicted.nn.values$net.result)

