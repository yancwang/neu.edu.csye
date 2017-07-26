# https://www.r-bloggers.com/hierarchical-clustering-in-r-2/
#install.packages("ggplot2")
install.packages("XLConnect")

library(ggplot2)
library(XLConnect)

play <- readWorksheetFromFile("C:/Users/Yanchen/Documents/R/play.xlsx", sheet = 1)

# complete linkage
clusters <- hclust(dist(play[, 2:3]))
plot(clusters)

clusterCut <- cutree(clusters, 2)
table(clusterCut, play$Decision)

# mean/average linkage
clusters <- hclust(dist(play[, 2:3]), method = 'average')
plot(clusters)

clusterCut <- cutree(clusters, 2)
table(clusterCut, play$Decision)

ggplot(play, aes(play$Temperature, play$Humidity, color = play$Decision)) + 
  geom_point(alpha = 0.4, size = 3.5) + geom_point(col = clusterCut) + 
  scale_color_manual(values = c('black', 'red', 'green'))

