file <- "C:/Users/Yanchen/Downloads/7390/Assignment/FinalProject/loan.csv";
loan <- read.csv(file, 1)

#check missing values
apply(loan, 2, function(x) sum(is.na(x)))

#separate train into two sub data in continuous and categorical
loan.col.name <- colnames(loan)

loan.col.name.continuous <- c(loan.col.name[2], loan.col.name[3], loan.col.name[8], loan.col.name[9], loan.col.name[12], loan.col.name[13])
loan.continuous <- loan[loan.col.name.continuous]

loan.col.name.categorical <- loan.col.name[!loan.col.name%in%c(loan.col.name.continuous, loan.col.name[14])]
loan.categorical <- loan[loan.col.name.categorical]

#categorical
loan.categorical.colname = colnames(loan.categorical)

for(i in 1:length(loan.categorical)){
  colvalue = sort(unique(loan.categorical[, i]))
  catNum = length(unique(loan.categorical[, i]))
  
  addcolomn = c(paste(loan.categorical.colname[i], c(1:catNum),sep = "_"))
  loan.categorical[, addcolomn]=0
  for(j in 1:catNum){
    replacelist = which(loan.categorical[,i] == colvalue[j]) 
    loan.categorical[,addcolomn[j]] <- replace(loan.categorical[,addcolomn[j]] ,replacelist, 1 )
  }
}
loan.categorical[, c(1:7)] <- NULL

# continuous
for (i in 1:length(loan.continuous)) {
  loan.continuous.max <- max(loan.continuous[i])
  loan.continuous.min <- min(loan.continuous[i])
  loan.continuous[i] <- (loan.continuous.max - loan.continuous[i])/(loan.continuous.max - loan.continuous.min)
}

#merge tow sub-dataframe(categorical and continuous) into one data frame
loan$Id <- seq.int(nrow(loan))
loan.continuous$Id <- loan$Id
loan.categorical$Id <- loan$Id

loan.modifiy <- merge(loan.continuous, loan.categorical, by = 'Id')
loan.modifiy$Id <- NULL

loan.modifiy$Decision <- loan$Decision
loan.modifiy$Decision <- as.integer(as.factor(loan.modifiy$Decision)) - 1

#linear regression
linearRegression <- glm(loan.modifiy$Decision ~ ., data = loan.modifiy, family = binomial)

#dimension reduction
loan.summary <- summary(linearRegression)
loan.summary.coeff <- loan.summary$coefficients
loan.summary.coeff.data <- as.data.frame(loan.summary.coeff)
loan.summary.coeff.data.name <- rownames(loan.summary.coeff.data)
loan.summary.coeff.data.name <- loan.summary.coeff.data.name[loan.summary.coeff.data$`Pr(>|z|)` < 0.01]
loan.summary.coeff.data.name <- loan.summary.coeff.data.name[-1]

f <- paste(loan.summary.coeff.data.name, collapse = ' + ')
f <- paste('Decision ~', f)
f <- as.formula(f)

linearRegression.fit <- glm(f, data = loan.modifiy, family = binomial)
summary(linearRegression.fit)

#seperate into train and test
set.seed(123)
index <- sample(1:nrow(loan.modifiy),round(0.8 * nrow(loan.modifiy)))
loan.train <- loan.modifiy[index,]
loan.test <- loan.modifiy[-index,]

#neural network
library(neuralnet)
nn <- neuralnet(f, data = loan.train, hidden=c(2), stepmax = 1e6, linear.output = FALSE)
plot(nn)

loan.test.fit <- loan.test[c(loan.summary.coeff.data.name, 'Decision')]
loan.predict <- compute(nn, loan.test.fit[1:3])
loan.test.fit$Predict <- sapply(loan.predict$net.result, round, digits = 0)

table(loan.test.fit$Decision, loan.test.fit$Predict)

#neural network compare
loan.col.name.modify <- colnames(loan.train)
loan.col.name.modify <- loan.col.name.modify[-36]

f <- paste(loan.col.name.modify, collapse = ' + ')
f <- paste('Decision ~', f)
f <- as.formula(f)

nn <- neuralnet(f, data = loan.train, hidden=c(5, 3), stepmax = 1e6, linear.output = FALSE)
plot(nn)

loan.test.fit.compare <- loan.test[c(loan.col.name.modify, 'Decision')]
loan.predict.compare <- compute(nn, loan.test.fit.compare[1:35])
loan.test.fit.compare$Predict <- sapply(loan.predict.compare$net.result, round, digits = 0)

table(loan.test.fit.compare$Decision, loan.test.fit.compare$Predict)
