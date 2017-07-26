library("Rstem")

file <- "C:/Users/Yanchen/Documents/R/northeastern_class.csv"
res <- read.csv(file)

positive <- scan('C:/Users/Yanchen/Documents/R/positive-words.txt',what='character',comment.char=';')
negative <- scan('C:/Users/Yanchen/Documents/R/negative-words.txt',what='character',comment.char=';')

res$PosPercent = NA
res$NegPercent = NA

for(i in 1 : nrow(res)){
  words <- strsplit(as.character(res[i, ]$Body), " ")
  words <- rapply(words, f = wordStem)
  words <- gsub("[[:punct:]]", "", words)
  pos <- sum(!is.na(match(words, positive)))
  neg <- sum(!is.na(match(words, negative)))
  res[i, ]$PosPercent <- (pos/length(words))
  res[i, ]$NegPercent <- (neg/length(words))
}

posAverage <- sum(res$PosPercent[!is.na(res$PosPercent)]) / sum(!is.na(res$PosPercent))
negAverage <- sum(res$NegPercent[!is.na(res$NegPercent)]) / sum(!is.na(res$NegPercent))

library(ggplot2)
dt = data.frame(Amount = c(sum(res$PosPercent > res$NegPercent), sum(res$PosPercent < res$NegPercent)), Category = c('Positive','Negative'))
p = ggplot(dt, aes(x = "", y = Amount, fill = Category)) + geom_bar(stat = "identity", width = 1) + coord_polar(theta = "y")   
p

individual <- res[1, ]
dt = data.frame(Percent = c(individual$PosPercent, individual$NegPercent, (1 - individual$PosPercent - individual$NegPercent)), Category = c('Positive','Negative', 'Other'))
p = ggplot(dt, aes(x = "", y = Percent, fill = Category)) + geom_bar(stat = "identity", width = 1) + coord_polar(theta = "y")   
p

dt <- data.frame(Percent = c(individual$PosPercent, individual$NegPercent), Category = c('Positive','Negative'))
p = ggplot(data = dt, mapping = aes(x = Category, y = Percent)) + geom_bar(stat= 'identity')
p
