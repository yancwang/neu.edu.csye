#(1)
c(1:20)
c(20:1)
c(1:20, 20:1)
tmp <- c(4,6,3)
rep(tmp, 10)
append(rep(tmp, 10), 4)
append(append(rep(4, 10), rep(6, 20)), rep(3, 30))

#(2)
x1 <- seq(3.0, 6.0, 0.1)
exp(x) * cos(x)

#(3)
x1 <- 0.1 ^ c(3:36)
x2 <- 0.2 ^ c(1:34)
x1 * x2

x1 <- 2 ^ c(1:25)
x2 <- c(1:25)
x1 / x2

#(4)
x1 <- c(10:100)
sum(x1 ^ 3 + 4 * x1 ^ 2)

x1 <- c(1:25)
sum(2 ^ x1 / x1 + 3 ^ x1 / x1 ^ 2)

#(5)
paste("label", 1:30)
paste0("fn", 1:30)

#(6)
set.seed(50)
xVec <- sample(0:999, 250, replace = T)
yVec <- sample(0:999, 250, replace = T)

x <- xVec[-250]
y <- yVec[-1]
y - x

x <- xVec[-1]
y <- yVec[-250]
sin(y) / cos(x)

x1 <- xVec[-c(249, 250)]
x2 <- xVec[-c(1, 250)]
x3 <- xVec[-c(1, 2)]
B = matrix(c(x1, x2, x3), nrow=248, ncol=3)
b <- c(1, 2, -1)
as.vector(B %*% b)

x1 <- xVec[-1]
x2 <- xVec[-250]
sum(exp(-x1) / (x2 + 10))

#(7)
yVec[yVec > 600]

which(yVec>600)

xVec[which(yVec>600)]

sqrt(abs(xVec - mean(xVec)))

length(yVec[yVec > max(yVec) - 200])

sum(xVec %% 2  == 0)

sort(order(yVec)[xVec])

yVec[seq(1, 250, 3)]
