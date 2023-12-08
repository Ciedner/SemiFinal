package com.test.mabale_ciedner_semi_final

data class Tweet (
    val id: String,
    val name: String,
    val description: String,
    val timestamp: Map<String, Long>
)