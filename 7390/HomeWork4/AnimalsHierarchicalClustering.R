#https://cran.r-project.org/web/packages/dendextend/vignettes/Cluster_Analysis.html#animals---attributes-of-animals
#install.packages("dendextend")
#install.packages("corrplot")
library(dendextend)
library(corrplot)

animals <- cluster::animals

colnames(animals) <- c("warm-blooded", 
                       "can fly",
                       "vertebrate",
                       "endangered",
                       "live in groups",
                       "have hair")

dend_r <- animals %>% dist(method = "man") %>% hclust(method = "ward.D") %>% as.dendrogram %>% ladderize %>%
    color_branches(k=4)

dend_c <- t(animals) %>% dist(method = "man") %>% hclust(method = "com") %>% as.dendrogram %>% ladderize%>%
    color_branches(k=3)


# some_col_func <- function(n) rev(colorspace::heat_hcl(n, c = c(80, 30), l = c(30, 90), power = c(1/5, 1.5)))
# some_col_func <- colorspace::diverge_hcl
# some_col_func <- colorspace::sequential_hcl
some_col_func <- function(n) (colorspace::diverge_hcl(n, h = c(246, 40), c = 96, l = c(65, 90)))

#install.packages("gplots")
#par(mar = c(3,3,3,3))
#library(gplots)
gplots::heatmap.2(as.matrix(animals-1), 
          main = "Attributes of Animals",
          srtCol = 35,
          Rowv = animals,
          Colv = t(animals),
          trace="row", hline = NA, tracecol = "darkgrey",         
          margins =c(6,3),      
          key.xlab = "no / yes",
          denscol = "grey",
          density.info = "density",
          col = some_col_func
         )

hclust_methods <- c("ward.D", "single", "complete", "average", "mcquitty", 
        "median", "centroid", "ward.D2")
animals_dendlist <- dendlist()

for(i in seq_along(hclust_methods)) {
   tmp_dend <-  animals %>% dist(method = "man") %>% 
      hclust(method = hclust_methods[i]) %>% as.dendrogram 
   animals_dendlist <- dendlist(animals_dendlist, tmp_dend)
}
names(animals_dendlist) <- hclust_methods
# votes.repub_dendlist

cophenetic_cors <- cor.dendlist(animals_dendlist)
corrplot::corrplot(cophenetic_cors, "pie", "lower")

remove_median <- dendlist(animals_dendlist, which = c(1:8)[-6] )
FM_cors <- cor.dendlist(remove_median, method = "FM_index", k = 4)
corrplot::corrplot(FM_cors, "pie", "lower")

