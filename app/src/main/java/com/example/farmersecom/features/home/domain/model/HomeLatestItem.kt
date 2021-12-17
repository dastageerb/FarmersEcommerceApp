package com.example.farmersecom.features.home.domain.model

data class HomeLatestItem
    ( var categoryId:String,
      var categoryName:String,
      var products:List<HomeChildProduct>)