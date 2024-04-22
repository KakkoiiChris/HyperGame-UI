package kakkoiichris.hypergame.ui

import kakkoiichris.hypergame.input.Input
import kakkoiichris.hypergame.media.Renderable
import kakkoiichris.hypergame.media.Renderer
import kakkoiichris.hypergame.media.desaturated
import kakkoiichris.hypergame.state.StateManager
import kakkoiichris.hypergame.util.Time
import kakkoiichris.hypergame.util.math.Box
import kakkoiichris.hypergame.view.View
import java.awt.BasicStroke
import java.awt.Color

abstract class Module : Box(), Renderable {
    var parent: Module? = null

    var children = mutableListOf<Module>()

    var id = ""

    var padding = 0U
        set(value) {
            field = value
            paddingTop = value
            paddingRight = value
            paddingBottom = value
            paddingLeft = value
        }

    val paddingVertical get() = paddingTop + paddingBottom
    val paddingHorizontal get() = paddingLeft + paddingRight

    var paddingTop = 0U
    var paddingRight = 0U
    var paddingBottom = 0U
    var paddingLeft = 0U

    var margin = 0U
        set(value) {
            field = value
            marginTop = value
            marginRight = value
            marginBottom = value
            marginLeft = value
        }

    val marginVertical get() = marginTop + marginBottom
    val marginHorizontal get() = marginLeft + marginRight

    var marginTop = 0U
    var marginRight = 0U
    var marginBottom = 0U
    var marginLeft = 0U

    var enabled: Boolean
        get() = selfEnabled && parent?.enabled != false
        set(value) {
            selfEnabled = value
        }

    private var selfEnabled = true

    var foreground: Color
        get() = if (enabled) foregroundOn else foregroundOff
        set(value) {
            foregroundOn = value
            foregroundOff = value.desaturated().darker()
        }

    var background: Color
        get() = if (enabled) backgroundOn else backgroundOff
        set(value) {
            backgroundOn = value
            backgroundOff = value.desaturated().darker()
        }

    var accent: Color
        get() = if (enabled) accentOn else accentOff
        set(value) {
            accentOn = value
            accentOff = value.desaturated().darker()
        }

    var foregroundOn = Color(72, 75, 90)
    var backgroundOn = Color(222, 232, 242)
    var accentOn = Color(28, 152, 186)

    var foregroundOff = foregroundOn.desaturated().darker()
    var backgroundOff = backgroundOn.desaturated().darker()
    var accentOff = accentOn.desaturated().darker()

    var stroke = BasicStroke(4F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)
    var cornerRadius = 16

    fun setPaddings(top: UInt = 0U, right: UInt = 0U, bottom: UInt = 0U, left: UInt = 0U) {
        paddingTop = top
        paddingRight = right
        paddingBottom = bottom
        paddingLeft = left
    }

    fun setMargins(top: UInt = 0U, right: UInt = 0U, bottom: UInt = 0U, left: UInt = 0U) {
        marginTop = top
        marginRight = right
        marginBottom = bottom
        marginLeft = left
    }

    fun add(module: Module, option: UInt = 0U) {
        module.parent = this

        if (option == 0U) {
            children += module
        }
        else {
            children[option.toInt() - 1] = module
        }
    }

    fun remove(module: Module) =
        children.remove(module)

    fun find(id: String): Module? {
        if (this.id == id) return this

        if (children.isEmpty()) return null

        for (child in children) {
            return child.find(id) ?: continue
        }

        return null
    }

    override fun update(view: View, manager: StateManager, time: Time, input: Input) {
        for (child in children) {
            child.update(view, manager, time, input)
        }
    }

    override fun render(view: View, renderer: Renderer) {
        renderer.color = background

        renderer.fillRoundRect(this, cornerRadius, cornerRadius)

        renderer.color = foreground
        renderer.stroke = stroke

        renderer.drawRoundRect(this, cornerRadius, cornerRadius)

        renderer.color = accent

        renderer.drawRect(
            (left - marginLeft.toDouble()).toInt(),
            (top - marginTop.toDouble()).toInt(),
            (width + marginLeft.toDouble() + marginRight.toDouble()).toInt(),
            (height + marginTop.toDouble() + marginBottom.toDouble()).toInt()
        )

        for (child in children) {
            child.render(view, renderer)
        }
    }
}
