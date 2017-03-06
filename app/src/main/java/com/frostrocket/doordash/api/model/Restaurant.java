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

    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;

    @SerializedName("yelp_biz_id")
    @Expose
    private String yelpBusinessId;

    @SerializedName("delivery_radius")
    @Expose
    private Integer deliveryRadius;

    @SerializedName("inflation_rate")
    @Expose
    private Double inflationRate;

    public Boolean getTimeSurging() {
        return isTimeSurging;
    }

    public void setTimeSurging(Boolean timeSurging) {
        isTimeSurging = timeSurging;
    }

    public String getMaxOrderSize() {
        return maxOrderSize;
    }

    public void setMaxOrderSize(String maxOrderSize) {
        this.maxOrderSize = maxOrderSize;
    }

    public Integer getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(Integer deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public Integer getMaxCompositeScore() {
        return maxCompositeScore;
    }

    public void setMaxCompositeScore(Integer maxCompositeScore) {
        this.maxCompositeScore = maxCompositeScore;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ArrayList<Menu> getMenus() {
        return menus;
    }

    public void setMenus(ArrayList<Menu> menus) {
        this.menus = menus;
    }

    public Integer getCompositeScore() {
        return compositeScore;
    }

    public void setCompositeScore(Integer compositeScore) {
        this.compositeScore = compositeScore;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public Boolean getOnlyCatering() {
        return isOnlyCatering;
    }

    public void setOnlyCatering(Boolean onlyCatering) {
        isOnlyCatering = onlyCatering;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAsapTime() {
        return asapTime;
    }

    public void setAsapTime(String asapTime) {
        this.asapTime = asapTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public Integer getYelpReviewCount() {
        return yelpReviewCount;
    }

    public void setYelpReviewCount(Integer yelpReviewCount) {
        this.yelpReviewCount = yelpReviewCount;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Double getExtraSosDeliveryFee() {
        return extraSosDeliveryFee;
    }

    public void setExtraSosDeliveryFee(Double extraSosDeliveryFee) {
        this.extraSosDeliveryFee = extraSosDeliveryFee;
    }

    public Double getYelpRating() {
        return yelpRating;
    }

    public void setYelpRating(Double yelpRating) {
        this.yelpRating = yelpRating;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getHeaderImageUrl() {
        return headerImageUrl;
    }

    public void setHeaderImageUrl(String headerImageUrl) {
        this.headerImageUrl = headerImageUrl;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Integer getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(Integer priceRange) {
        this.priceRange = priceRange;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getServiceRate() {
        return serviceRate;
    }

    public void setServiceRate(Double serviceRate) {
        this.serviceRate = serviceRate;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public String getFeaturedCategoryDescription() {
        return featuredCategoryDescription;
    }

    public void setFeaturedCategoryDescription(String featuredCategoryDescription) {
        this.featuredCategoryDescription = featuredCategoryDescription;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getYelpBusinessId() {
        return yelpBusinessId;
    }

    public void setYelpBusinessId(String yelpBusinessId) {
        this.yelpBusinessId = yelpBusinessId;
    }

    public Integer getDeliveryRadius() {
        return deliveryRadius;
    }

    public void setDeliveryRadius(Integer deliveryRadius) {
        this.deliveryRadius = deliveryRadius;
    }

    public Double getInflationRate() {
        return inflationRate;
    }

    public void setInflationRate(Double inflationRate) {
        this.inflationRate = inflationRate;
    }
}