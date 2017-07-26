# http://www.statmethods.net/input/datatypes.html
# vectors

a <- c(1,2,5.3,6,-2,4) # numeric vector
b <- c("one","two","three") # character vector
c <- c(TRUE,TRUE,TRUE,FALSE,TRUE,FALSE) #logical vector

a[c(2,4)] # 2nd and 4th elements of vector

# matrix

cells <- c(1,26,24,68)
rnames <- c("R1", "R2")
cnames <- c("C1", "C2")
mymatrix <- matrix(cells, nrow=2, ncol=2, byrow=TRUE, dimnames=list(rnames, cnames)) 

x<-matrix(1:20, nrow=5,ncol=4)
x[,4] # 4th column of matrix
x[3,] # 3rd row of matrix
x[2:4,1:3] # rows 2,3,4 of columns 1,2,3 

# frames

d <- c(1,2,3,4)
e <- c("red", "white", "red", NA)
f <- c(TRUE,TRUE,TRUE,FALSE)
mydata <- data.frame(d,e,f)
names(mydata) <- c("ID","Color","Passed") # variable names 
mydata

# lists

# example of a list with 4 components -
# a string, a numeric vector, a matrix, and a scaler
w <- list(name="Fred", mynumbers=d, mymatrix=x, age=5.3)
w1 <- list(f)

# example of a list containing two lists
v <- c(w,w1)

w[[2]] # 2nd component of the list
w[["mynumbers"]] # component named mynumbers in list

# factors

# variable gender with 20 "male" entries and
# 30 "female" entries
gender <- c(rep("male",20), rep("female", 30))
class(gender)
gender <- factor(gender)
class(gender)
# stores gender as 20 1s and 30 2s and associates
# 1=female, 2=male internally (alphabetically)
# R now treats gender as a nominal variable
summary(gender)

# variable rating coded as "large", "medium", "small'
rating <- c("large", "medium", "small")
class(rating)
rating <- ordered(rating)
class(rating)
# recodes rating to 1,2,3 and associates
# 1=large, 2=medium, 3=small internally
# R now treats rating as ordinal

# useful functions

object <- x
length(object) # number of elements or components
str(object)    # structure of an object
class(object)  # class or type of an object
names(object)  # names

o2 <- c(object,object)       # combine objects into a vector
cbind(object, object) # combine objects as columns
rbind(object, object) # combine objects as rows

object     # prints the object

ls()       # list current objects
fix(object)               # edit in place 
rm(object) # delete an object

newobject <- edit(object) # edit copy and save as newobject

# for loop

for (i in 1:3)
  print(i)

# if-then

if (i < 10) {
  print("Less")
} else {
  print("Greater")
}

#if-else

ifelse(i < 10, "first", "second")

# repeat

repeat {
  print(i);
  i <- i+1;
  if (i > 25) break;
}

# while

i <- 0
while(i < 10) {
  print(i)
  i <- i+ 1
}

# subsetting - select, project, merge for join

x <- data.frame(1:3, c(T, F, T), c('a', 'b', 'c'))
colnames(x) <- c("c1", "c2", "c3")
subset(x, c1 > 1, c(c2, c3))

y <- data.frame(c("a", "b", "c"), 1:3) # column major
colnames(y) <- c("d1", "d2")

merge(x, y, by.x = "c3", by.y = "d1")

# function

f <- function(i) {return (i + 1)}

# list

L <- list(1, 2, 3)
L
class(L[1])
class(L[[1]])

# importing data from xlsx

install.packages("xlsx")
library("xlsx")

file <- system.file("tests", "test_import.xlsx", package = "xlsx")
setwd('C:/Users/Yanchen/Documents/R')
file <- "SpeciesData.xlsx"
res <- read.xlsx(file, 1)  # read first sheet
class(res)
str(res)
res$Area[10:20]
head(res[, 1:6])

# arrays and parallelism

greatervalue <- function(x, y) {
  y.is.bigger <- y > x
  x[y.is.bigger] <- y[y.is.bigger]
  x
}

gvarray <- function(x, y) {
  for (i in 1:length(x))
    if (x[i] < y[i])
      x[i] = y[i]
  x
}

gvsplit <- function(x, y) {
  x1 <- x[1:(length(x)/2)]
  y1 <- y[1:(length(y)/2)]
  z1 <- greatervalue(x1,y1)
  
  x2 <- x[(length(x)/2 + 1):length(x)]
  y2 <- y[(length(y)/2 + 1):length(y)]
  z2 <- greatervalue(x2,y2)
  
  c(z1,z2)
}

x <- c(4,2,3,7)
y <- c(1,9,2,5)

greatervalue(x,y)
gvarray(x,y)
gvsplit(x,y)

# arrays, matrices, lists

#plots

Var1 <- c(rnorm(50, 1, 0.5), rnorm(50, -0.6, 0.2))
Var2 <- c(rnorm(50, -0.8, 0.2), rnorm(50, 2, 1))
x <- matrix(c(Var1, Var2), nrow = 100, ncol = 2)
par(pty="s")
plot(x[,1], x[,2], asp=1)

# pretty cluster
# install.packages("fpc")
library(cluster)
library(fpc)

all = c(x[,1], x[,2])
range = c(min(all), max(all))
plot(x[,1], x[,2], xlim=range, ylim=range)

clus <- kmeans(x, centers=2)
#plot(clus)
plotcluster(x, clus$cluster, method="dc", clnum = 2, asp=1)
clusplot(x, clus$cluster, color=TRUE, labels=1, lines=0, asp=1)

# calls by names and references

call_by_name <- function(L) {
  L <- L + 1
}

call_by_reference <- function(L) {
  eval.parent(substitute(L <- L + 1))
}

L <- 10
L
call_by_name(L)
L

L1 <- 10
L1
call_by_reference(L1)
L1

# recursive function

factorial <- function(n) {
  if (n == 0)
    return (1)
  else
    return (n * factorial(n - 1))
}

factorial(5)

#permutation([], []).
#permutation(L, [X|Xs]) :- select(X, L, Rest), permutation(Rest, Xs).

permutation <- function(L, Result) {
  if (length(L) == 1)
    return (L[1])
  else {
    for (i in 1:length(L)) {
      X <- L[i]
      Rest <- L[-i]
      Xs <- permutation(Rest, Result)
      eval.parent(substitute(Result <- list(c(X,Xs), Result)))
    }
  }
}

Result <- list(c(1,2,3))
L <- list(c(1,2,3,4,5))
permutation(L, Result)

aggregate(pkmn$Attack, by=list(Category=pkmn$Type), FUN=sum)

