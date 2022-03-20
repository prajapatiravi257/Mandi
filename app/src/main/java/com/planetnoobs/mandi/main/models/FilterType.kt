package com.planetnoobs.mandi.main.models


class FilterType {
    var id: Long = 0
    var name: String? = null
    var type: String? = null
    var label: String? = null

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as FilterType
        if (id != that.id) return false
        if (if (name != null) name != that.name else that.name != null) return false
        return if (if (type != null) type != that.type else that.type != null) false else !if (label != null) label != that.label else that.label != null
    }

    override fun hashCode(): Int {
        var result = (id xor (id ushr 32)).toInt()
        result = 31 * result + if (name != null) name.hashCode() else 0
        result = 31 * result + if (type != null) type.hashCode() else 0
        result = 31 * result + if (label != null) label.hashCode() else 0
        return result
    }

    override fun toString(): String {
        return "FilterType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", label='" + label + '\'' +
                '}'
    }
}