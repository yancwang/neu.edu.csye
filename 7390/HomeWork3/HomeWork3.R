#Part1
library("MASS")
b <- Boston
fit <- lm(b$medv ~ b$crim + b$zn + b$indus + b$chas + b$nox + b$rm + b$age + b$dis + b$rad + b$tax + b$ptratio + b$black + b$lstat)
summary(fit)
fit <- lm(b$medv ~ b$crim + b$zn + b$chas + b$nox + b$rm + b$dis + b$rad + b$tax + b$ptratio + b$black + b$lstat)
summary(fit)
fit <- lm(b$medv ~ b$zn + b$nox + b$rm + b$dis + b$rad + b$tax + b$ptratio + b$black + b$lstat)
summary(fit)
fit <- lm(b$medv ~ b$nox + b$rm + b$dis + b$rad + b$tax + b$ptratio + b$black + b$lstat)
summary(fit)
fit <- lm(b$medv ~ b$nox + b$rm + b$dis + b$rad + b$ptratio + b$black + b$lstat)
summary(fit)
fit <- lm(b$medv ~ b$nox + b$rm + b$dis + b$ptratio + b$black + b$lstat)
summary(fit)

#Part2
setwd('C:/Users/Yanchen/Documents/R')
file <- "bank-data.csv"
mydata <- read.csv(file, head=TRUE, sep=",")
mydata$id <- NULL
mydata$age <- mydata$age / 100
mydata$income <- mydata$income / 10000

mydata$sex.factor <- factor(mydata$sex)
mydata$sex <- as.numeric(mydata$sex.factor)

mydata$region.fator <- factor(mydata$region)
mydata$region <- as.numeric(mydata$region.fator)

mydata$married.factor <- factor(mydata$married)
mydata$married <- as.numeric(mydata$married.factor)

mydata$car.factor <- factor(mydata$car)
mydata$car <- as.numeric(mydata$car.factor) 

mydata$save_act.factor <- factor(mydata$save_act)
mydata$save_act <- as.numeric(mydata$save_act.factor)

mydata$current_act.factor <- factor(mydata$current_act)
mydata$current_act <- as.numeric(mydata$current_act.factor)

mydata$mortgage.factor <- factor(mydata$mortgage)
mydata$mortgage <- as.numeric(mydata$mortgage.factor)

mydata$pep.factor <- factor(mydata$pep)
mydata$pep <- as.numeric(mydata$pep.factor)

Cluster <- kmeans(mydata[, 1:11], 6, nstart = 20)
