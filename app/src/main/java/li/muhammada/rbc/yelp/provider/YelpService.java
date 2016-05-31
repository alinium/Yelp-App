package li.muhammada.rbc.yelp.provider;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YelpService {

    @GET("search/")
    Call<ResponseWrapper> search(@Query("term") String searchTerm, @Query("location") String location);

}
