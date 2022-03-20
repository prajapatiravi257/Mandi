package com.planetnoobs.mandi.main.models


class Filters {
    // Values parsed from API
    private var filters: List<FilterType>? = null

    constructor() {}
    constructor(filters: List<FilterType>?) {
        this.filters = filters
    }

    fun getFilters(): List<FilterType>? {
        return filters
    }

    fun setFilters(filters: List<FilterType>?) {
        this.filters = filters
    }
}