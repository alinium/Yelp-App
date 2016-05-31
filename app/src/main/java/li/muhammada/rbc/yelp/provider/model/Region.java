
package li.muhammada.rbc.yelp.provider.model;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Region {

    @SerializedName("span")
    @Expose
    private Span span;
    @SerializedName("center")
    @Expose
    private Coordinate center;

    /**
     * 
     * @return
     *     The span
     */
    public Span getSpan() {
        return span;
    }

    /**
     * 
     * @param span
     *     The span
     */
    public void setSpan(Span span) {
        this.span = span;
    }

    /**
     * 
     * @return
     *     The center
     */
    public Coordinate getCenter() {
        return center;
    }

    /**
     * 
     * @param center
     *     The center
     */
    public void setCenter(Coordinate center) {
        this.center = center;
    }

}
