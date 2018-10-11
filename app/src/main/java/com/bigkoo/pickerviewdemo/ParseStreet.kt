package com.bigkoo.pickerviewdemo

import com.bigkoo.pickerviewdemo.bean.Street
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class OptionItems<T1, T2, T3, T4>(
    val options1Items: List<T1>,
    val options2Items: List<List<T2>>,
    val options3Items: List<List<List<T3>>>,
    val options4Items: List<List<List<List<T4>>>>
)

fun parseStreet(json: String): OptionItems<String, String, String, Street> {
    val result: Map<String, Map<String, List<Street>>> = Gson().fromJson(
        json,
        (object : TypeToken<Map<String, Map<String, List<Street>>>>() {}).type
    )

    val allStreet = result.values.flatMap { it.values }.flatMap { it }

    val options1Items =
        allStreet.groupBy { it.province }
            .keys.asSequence()
            .filterNotNull()
            .toList()//省份

    val options2Items =
        allStreet.groupBy { it.province }.values.map { it ->
            it.asSequence()
                .groupBy { it.city }
                .keys.asSequence()
                .filterNotNull()
                .toList()
        }.toList()

    val options3Items = allStreet.asSequence().groupBy { it.province }
        .map { it ->
            it.value.asSequence()
                .groupBy { it.city }
                .map { cityMap ->
                    cityMap.value.groupBy { it.district }
                        .keys.asSequence()
                        .filterNotNull()
                        .toList()
                }.toList()
        }.toList()

    val options4Items = allStreet.asSequence().groupBy { it.province }
        .map { it ->
            it.value.asSequence().groupBy { it.city }
                .map { cityMap ->
                    cityMap.value.asSequence()
                        .groupBy { it.district }
                        .map { it.value }
                        .toList()
                }.toList()
        }.toList()

    return OptionItems(options1Items, options2Items, options3Items, options4Items)
}