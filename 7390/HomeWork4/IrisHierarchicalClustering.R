# https://www.r-bloggers.com/hierarchical-clustering-in-r-2/
#install.packages("ggplot2")
library(ggplot2)

# complete linkage
clusters <- hclust(dist(iris[, 3:4]))
plot(clusters)

clusterCut <- cutree(clusters, 3)
table(clusterCut, iris$Species)

# mean/average linkage
clusters <- hclust(dist(iris[, 3:4]), method = 'average')
plot(clusters)

clusterCut <- cutree(clusters, 3)
table(clusterCut, iris$Species)

ggplot(iris, aes(Petal.Length, Petal.Width, color = iris$Species)) + 
  geom_point(alpha = 0.4, size = 3.5) + geom_point(col = clusterCut) + 
  scale_color_manual(values = c('black', 'red', 'green'))

