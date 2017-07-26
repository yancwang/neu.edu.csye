p <- c (3, 5, 6, 8)
q <- c (3, 3, 3)
q <- append(q, 0, length(p))
p+q

Age <- c(22, 25, 18, 20)
Name <- c("James", "Mathew", "Olivia", "Stella")
Gender <- c("M", "M", "F", "F")
a <- data.frame(Age, Name, Gender)
subset(a, Age > 20)

x <- c(34, 56, 55, 87, NA, 4, 77, NA, 21, NA, 39)
sum(is.na(x))

data(islands) 
length(islands)

summary(islands)
sd(islands)
range(islands)

quantile(islands)
hist(islands)
hist(islands, prob = T)

boxplot(islands)
boxplot(islands, outline = F)
