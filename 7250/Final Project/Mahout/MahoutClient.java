package neu.edu.mahout;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class MahoutClient {

	public static void main(String[] args) throws IOException, TasteException {
		DataModel model = new FileDataModel(new File(args[0]));
		//user based models
		UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
		UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
		Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
		List<RecommendedItem> recommendations = recommender.recommend(Long.parseLong(args[1]), 5);
		System.out.println("User based models");
		for(RecommendedItem recommendation: recommendations) {
			System.out.println(recommendation);
		}
		//item based models
		ItemSimilarity itemSimilarity = new PearsonCorrelationSimilarity(model);
		recommender = new GenericItemBasedRecommender(model, itemSimilarity);
		recommendations = ((GenericItemBasedRecommender) recommender).mostSimilarItems(Long.parseLong(args[1]), 5);
		System.out.println("Item based models");
		for(RecommendedItem recommendation: recommendations) {
			System.out.println(recommendation);
		}
	}

}
