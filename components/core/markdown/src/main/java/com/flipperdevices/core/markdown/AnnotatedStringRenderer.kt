package com.flipperdevices.core.markdown

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.LocaleList
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextGeometricTransform
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import com.vladsch.flexmark.ast.Emphasis
import com.vladsch.flexmark.ast.StrongEmphasis
import com.vladsch.flexmark.ast.Text
import com.vladsch.flexmark.util.ast.Node
import com.vladsch.flexmark.util.sequence.BasedSequence

/**
 * Now support only:
 * 1) Bold (** tag)
 * 2) Italic (* tag)
 */
object AnnotatedStringRenderer {
    fun render(node: Node): AnnotatedString {
        val builder = AnnotatedString.Builder()
        render(node, builder)
        return builder.toAnnotatedString()
    }

    private fun render(node: Node, builder: AnnotatedString.Builder) {
        when (node) {
            is Text -> builder.append(node.chars.toString())
            is Emphasis -> builder.appendText(node.text, fontStyle = FontStyle.Italic)
            is StrongEmphasis -> builder.appendText(node.text, fontWeight = FontWeight.Bold)
            else -> renderChildren(node, builder)
        }
    }

    private fun renderChildren(node: Node, builder: AnnotatedString.Builder) {
        var child = node.firstChild
        while (child != null) {
            render(child, builder)
            child = child.next
        }
    }
}

@Suppress("LongParameterList")
private fun AnnotatedString.Builder.appendText(
    text: BasedSequence,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight? = null,
    fontStyle: FontStyle? = null,
    fontSynthesis: FontSynthesis? = null,
    fontFamily: FontFamily? = null,
    fontFeatureSettings: String? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    baselineShift: BaselineShift? = null,
    textGeometricTransform: TextGeometricTransform? = null,
    localeList: LocaleList? = null,
    background: Color = Color.Unspecified,
    textDecoration: TextDecoration? = null,
    shadow: Shadow? = null
) {
    withStyle(
        SpanStyle(
            color,
            fontSize,
            fontWeight,
            fontStyle,
            fontSynthesis,
            fontFamily,
            fontFeatureSettings,
            letterSpacing,
            baselineShift,
            textGeometricTransform,
            localeList,
            background,
            textDecoration,
            shadow
        )
    ) {
        append(text.toString())
    }
}