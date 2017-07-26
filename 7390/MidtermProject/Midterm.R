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
    addcolomn = c(paste(colname[i], c(1:catNum),sep = "-"))
    train_categorical[, addcolomn]=0
    for(j in 1:catNum){
      replacelist = which(train_categorical[,i] == colvalue[j]) 
      train_categorical[,addcolomn[j]] <- replace(train_categorical[,addcolomn[j]] ,replacelist, 1 )
    }
  }
  
}
train_categorical[, c(1:60)] <- NULL
summary(train_categorical)

#train_continuous
#assign values to na in each column
for (i in 1:length(train_continuous)) {
  train_continuous_mean <- mean(train_continuous[i][!is.na(train_continuous[i])])
  train_continuous[i][is.na(train_continuous[i])] <- train_continuous_mean
}
summary(train_continuous)

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
train_split = train_merge[6001:nrow(train_merge), ]

train_1 <- train_split
train_1$Id <- NULL
test_1 <- test
test_1$Id <- NULL

#linear regression
#first time regression
linearRegression <- lm(train_1$Response ~ ., data = train_1)
test_1$Predict <- round(abs(predict(linearRegression, test_1)))
ana <- abs(test_1$Predict - test_1$Response)
sqrt(sum(ana^2)/6000)

summarycoeff <- summary(linearRegression)
coeff <- summarycoeff$coefficients

#dimension reduction
coeff_data <- as.data.frame(coeff)
coeff_data_name <- gsub("`", "", rownames(coeff_data))
coeff_data_name <- coeff_data_name[coeff_data$`Pr(>|t|)` < 0.00005]
linear_train <- train_1[coeff_data_name]
linear_train$Response <- train_1$Response

#secondtime regression
linearReg <- lm(linear_train$Response ~ ., data = linear_train)
summary(linearReg)

#predict
test_1$Predict <- round(abs(predict(linearReg, test_1)))
ana <- abs(test_1$Predict - test_1$Response)
sqrt(sum(ana^2)/6000)

#analysis
suma <- NULL
for (i in 0:9) {
  suma <- c(suma, sum(ana == i))
}
plot(c(0:9), suma, type = "h", ylab = "Amount", xlab = "Difference between response and predict",  lwd = 25, lend = 2)

x <- NULL
for (i in 1:8) {
  x[i] <- list(test_1$Predict[test_1$Response == i])
}

for (i in 1:8) {
  suma <- NULL
  for (j in 0:9) {
    suma <- c(suma, sum(as.integer(as.vector(x[i])) == j))
  }
  plot(c(0:9), suma, type = "h", ylab = "Amount", xlab = "Difference between response and predict",  lwd = 25, lend = 2)
  cat ("Press [enter] to continue")
  line <- readline()
}  
plot(c(0:9), s, type = "h", ylab = "Amount", xlab = "Difference between response and predict",  lwd = 25, lend = 2)

#### SVM #####
library(e1071)

# Divide data to x (contains the all independent variables) and y only the dependent variable
x <- subset(train, select=-Response)
y <- train$Response

# Create SVM Model and show summary
svm_model1 <- svm(x,y)
#system.time(svm_model1 <- svm(x,y))
summary(svm_model1)

# See the confusion matrix result of prediction, using command table to compare the result of SVM
# prediction and the class data in y variable.
pred <- predict(svm_model1,x)
system.time(pred <- predict(svm_model1,x))

table(pred,y)

# TUning SVM to find the best cost and epsilon
svm_tune <- tune(svm, train.x=x, train.y=y, kernel="radial", ranges=list(cost=10^(-1:2), epsilon = seq(0,0.2,0.01)))
print(svm_tune)

svm_model_after_tune <- svm(Response ~ ., data=train, kernel="radial", cost=100, gamma=0.5)
summary(svm_model_after_tune)

#Run prediction again on new model
pred <- predict(svm_model_after_tune,x)
system.time(predict(svm_model_after_tune,x))
table(pred,y)

#plot data
plot ( data1 )
#add line through data points
lines ( data1 , col = "blue" ) 

# Add points for fitted svrmodel
points(x , predictedY , col = "red" , pch=4)
lines(x , predictedY , col = "red" )