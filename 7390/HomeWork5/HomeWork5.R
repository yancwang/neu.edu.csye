#Part1
#Demand
p1 = 5 / 16
p2 = 5 / 16
p3 = 6 / 16
s = - p1 * log2(p1) - p2 * log2(p2) - p3 * log2(p3) 

p4 = 3 / 7
p5 = 2 / 7
p6 = 2 / 7
s1 = - p4 * log2(p4) - p5 * log2(p5) - p6 * log2(p6)

a1 = 2 / 4
a2 = 1 / 4
a3 = 1 / 4
s2 = - a1 * log2(a1) - a2 * log2(a2) - a3 * log2(a3)

a4 = 2 / 5
a5 = 3 / 5
s3 = - a4 * log2(a4) - a5 * log2(a5)

gain = s - 7 / 16 * s1 - 4 / 16 * s2 - 5 / 16 * s3

#Strategic
p4 = 1 / 3
p5 = 1 / 3
p6 = 1 / 3
s1 = - p4 * log2(p4) - p5 * log2(p5) - p6 * log2(p6)

a1 = 2 / 7
a2 = 2 / 7
a3 = 3 / 7
s2 = - a1 * log2(a1) - a2 * log2(a2) - a3 * log2(a3)

gain = s - 9 / 16 * s1 - 7 / 16 * s2

#Campaign
p4 = 4 / 9
p5 = 4 / 9
p6 = 1 / 9
s1 = - p4 * log2(p4) - p5 * log2(p5) - p6 * log2(p6)

a1 = 1 / 7
a2 = 1 / 7
a3 = 5 / 7
s2 = - a1 * log2(a1) - a2 * log2(a2) - a3 * log2(a3)

gain = s - 9 / 16 * s1 - 7 / 16 * s2

#Part2
library("xlsx")
train <- read.csv('C:/Users/Yanchen/Documents/R/train.csv', 1)
test <- read.csv('C:/Users/Yanchen/Documents/R/test.csv', 1)

train$Age <- train$Age / 100
train$Fare <- train$Fare / 10000

plot(hist(train$Age))
plot(hist(train$Fare))

library(rpart)
myFormula <- train$Survived ~ train$Pclass + train$Sex + train$Age + train$SibSp + train$Parch + train$Fare + train$Embarked
rpart <- rpart(myFormula, data = train)
plot(rpart)
text(rpart, use.n=T, cex= 0.6)

optimal <- which.min(rpart$cptable[,"xerror"])
cp <- rpart$cptable[optimal, "CP"]
prune <- prune(rpart, cp = cp)
plot(rpart)
text(rpart, use.n = T)

pred <- predict(prune, newdata=test)
table(pred, train$Survived)
xlim <- range(train$Survived)
plot(pred ~ train$Survived, data=test, xlab="Observed", ylab="Predicted", ylim=xlim, xlim=xlim)

#Part3
library("xlsx")
library(rpart)
energy <- read.xlsx('C:/Users/Yanchen/Documents/R/ENB2012_data.xlsx', 1)

plot(hist(energy$X1))
plot(hist(energy$X2))
plot(hist(energy$X3))
plot(hist(energy$X4))
plot(hist(energy$X5))
plot(hist(energy$X6))

formula1 <- energy$Y1 ~ energy$X1 + energy$X2 + energy$X3 + energy$X4 + energy$X5 + energy$X6 + energy$X7 + energy$X8
formula2 <- energy$Y2 ~ energy$X1 + energy$X2 + energy$X3 + energy$X4 + energy$X5 + energy$X6 + energy$X7 + energy$X8

fit1 <- rpart(formula1, method = "anova", data = energy)
printcp(fit1) 
plotcp(fit1) 
summary(fit1)
#par(mfrow=c(1,2))
#rsq.rpart(fit1)
plot(fit1, uniform = TRUE)
text(fit1, use.n = TRUE, all = TRUE, cex= 0.6)


pfit1 <- prune(fit1, cp = 0.02)
plot(pfit1, uniform = TRUE)
text(pfit1, use.n = TRUE, all = TRUE, cex= 0.6)


fit2 <- rpart(formula2, method = "anova", data = energy)
printcp(fit2) 
plotcp(fit2) 
summary(fit2)
#par(mfrow=c(1,2))
#rsq.rpart(fit2)
plot(fit2, uniform=TRUE)
text(fit2, use.n=TRUE, all=TRUE, cex= 0.6)

pfit2 <- prune(fit2, cp = 0.02)
plot(pfit2, uniform = TRUE)
text(pfit2, use.n = TRUE, all = TRUE, cex= 0.6)

