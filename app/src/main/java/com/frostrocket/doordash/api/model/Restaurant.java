package com.frostrocket.doordash.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Restaurant {
    @SerializedName("is_time_surging")
    @Expose
    private Boolean isTimeSurging;

    @SerializedName("max_order_size")
    @Expose
    private String maxOrderSize;

    @SerializedName("delivery_fee")
    @Expose
    private Integer deliveryFee;

    @SerializedName("max_composite_score")
    @Expose
    private Integer maxCompositeScore;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("menus")
    @Expose
    private ArrayList<Menu> menus = new ArrayList<>();

    @SerializedName("composite_score")
    @Expose
    private Integer compositeScore;

    @SerializedName("status_type")
    @Expose
    private String statusType;

    @SerializedName("is_only_catering")
    @Expose
    private Boolean isOnlyCatering;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("asap_time")
    @Expose
    private String asapTime;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("business")
    @Expose
    private Business business;

    @SerializedName("tags")
    @Expose
    private ArrayList<String> tags = new ArrayList<>();

    @SerializedName("yelp_review_count")
    @Expose
    private Integer yelpReviewCount;

    @SerializedName("business_id")
    @Expose
    private Integer businessId;

    @SerializedName("extra_sos_delivery_fee")
    @Expose
    private Double extraSosDeliveryFee;

    @SerializedName("yelp_rating")
    @Expose
    private Double yelpRating;

    @SerializedName("cover_img_url")
    @Expose
    private String coverImageUrl;

    @SerializedName("header_img_url")
    @Expose
    private String headerImageUrl;

    @SerializedName("address")
    @Expose
    private Address address;

    @SerializedName("price_range")
    @Expose
    private Integer priceRange;

    @SerializedName("slug")
    @Expose
    private String slug;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("service_rate")
    @Expose
    private Double serviceRate;

    @SerializedName("promotion")
    @Expose
    private String promotion;

    @SerializedName("featured_category_description")
    @Expose
    private String featuredCategoryDescription;

    public Boolean getTimeSurging() {
        return isTimeSurging;
    }

    public String getMaxOrderSize() {
        return maxOrderSize;
    }

    public Integer getDeliveryFee() {
        return deliveryFee;
    }

    public Integer getMaxCompositeScore() {
        return maxCompositeScore;
    }

    public Integer getId() {
        return id;
    }

    public ArrayList<Menu> getMenus() {
        return menus;
    }

    public Integer getCompositeScore() {
        return compositeScore;
    }

    public String getStatusType() {
        return statusType;
    }

    public Boolean getOnlyCatering() {
        return isOnlyCatering;
    }

    public String getStatus() {
        return status;
    }

    public String getAsapTime() {
        return asapTime;
    }

    public String getDescription() {
        return description;
    }

    public Business getBusiness() {
        return business;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public Integer getYelpReviewCount() {
        return yelpReviewCount;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public Double getExtraSosDeliveryFee() {
        return extraSosDeliveryFee;
    }

    public Double getYelpRating() {
        return yelpRating;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public String getHeaderImageUrl() {
        return headerImageUrl;
    }

    public Address getAddress() {
        return address;
    }

    public Integer getPriceRange() {
        return priceRange;
    }

    public String getSlug() {
        return slug;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Double getServiceRate() {
        return serviceRate;
    }

    public String getPromotion() {
        return promotion;
    }

    public String getFeaturedCategoryDescription() {
        return featuredCategoryDescription;
    }
}