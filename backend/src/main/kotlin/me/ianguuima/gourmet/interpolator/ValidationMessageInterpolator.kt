package me.ianguuima.gourmet.interpolator

import java.util.*
import javax.validation.MessageInterpolator


class ValidationMessageInterpolator(private val defaultInterpolator: MessageInterpolator) : MessageInterpolator {

    override fun interpolate(messageTemplate: String, context: MessageInterpolator.Context): String? {
        println("called 2")
        return defaultInterpolator.interpolate(messageTemplate.toUpperCase(), context)
    }

    override fun interpolate(messageTemplate: String, context: MessageInterpolator.Context, locale: Locale): String? {
        println("called 3")
        return defaultInterpolator.interpolate(messageTemplate.toUpperCase(), context, locale)
    }

}