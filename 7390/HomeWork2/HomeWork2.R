#Normal distribution
x = seq(-10, 10, 0.5)
plot(x, pnorm(x), col="red", type = "l")
plot(x, dnorm(x), col="blue", type = "l")
#Example: for scores of a class in test

#Poisson distribution
x = seq(0, 60, 0.01)
y = rpois(x, lambda = 10)
plot(x, dnorm(y), col="red", type = "l")
plot(x, pnorm(y), col="blue", type = "l")
#Example: for the amount of buses appear in a hour

#Exponential distribution
x<-seq(-2,2,0.01)
plot(x, dexp(x, 0.5), col="red", type='l')
plot(x, pexp(x, 0.5), col="blue", type='l')
#Example: for the time between two buses appear in the same station

#Binomial distribution
x <- seq(0, 1000, 20)
plot (x, dbinom(x, 1000, pi/10), col="red", type = "l")
plot (x, pbinom(x, 1000, pi/10), col="blue", type = "l")
#Example: for the percent of coins in the people

#Uniform distribution
x<-seq(0, 10, 0.01)
plot(x, dunif(x,0,1), col="red", type='l')
plot(x, punif(x,0,1), col="red", type='l')

#Gamma distribution
x<-seq(0, 10, 0.01)
plot(x,dgamma(x,2,2),col="red", type='l')
plot(x,pgamma(x,2,2),col="blue", type='l')

#Beta Distribution
x<-seq(-5, 5, 0.01)
plot(x, dbeta(x,0.5,0.5), col="red", type='l')
plot(x, pbeta(x,0.5,0.5), col="red", type='l')

#Weibull distribution
x<- seq(0, 2.5, 0.01) 
plot(x, dweibull(x, 0.5), type="l", col="blue")
plot(x, pweibull(x, 0.5), type="l", col="red")

#F-distribution
x<-seq(0, 5, 0.01)
plot(x, df(x,1,1,0), col="red", type='l')
plot(x, pf(x,1,1,0), col="blue", type='l')

#Student's t-distribution
x<-seq(-5, 5, 0.01)
plot(x, dt(x,1,0), col="red", type='l')
plot(x, pt(x,1,0), col="red", type='l')

#Chi-square distribution
x<-seq(0, 10, 0.01)
plot(x, dchisq(x,1), col="red", type='l')
plot(x, pchisq(x,1), col="red", type='l')

#Hypergeometric distribution
x<-seq(-10, 10, 0.1)
plot(x, dhyper(x, m = 10, n = 7, k = 8), col="red", type='l')
plot(x, phyper(x, m = 10, n = 7, k = 8), col="red", type='l')

#Cauchy distribution
x<-seq(-10, 10, 0.1)
plot(x, dcauchy(x), col="red", type='l')
plot(x, pcauchy(x), col="red", type='l')

#Logistic distribution
x<-seq(-10, 10, 0.1)
plot(x, dlogis(x), col="red", type='l')
plot(x, plogis(x), col="red", type='l')

#Lognormal distribution
x<-seq(-10, 10, 0.1)
plot(x, dlnorm(x), col="red", type='l')
plot(x, plnorm(x), col="red", type='l')
