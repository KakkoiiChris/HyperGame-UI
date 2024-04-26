package kakkoiichris.hypergame.ui.form

import kakkoiichris.hypergame.input.Button
import kakkoiichris.hypergame.input.Input
import kakkoiichris.hypergame.media.Renderer
import kakkoiichris.hypergame.state.StateManager
import kakkoiichris.hypergame.ui.Module
import kakkoiichris.hypergame.ui.UIEvent
import kakkoiichris.hypergame.util.Time
import kakkoiichris.hypergame.view.View
import java.awt.Font

open class CheckBox(var text: String = "") : Module() {
    var font = Font("Monospaced", Font.PLAIN, 15)

    var onChange: (Event) -> Unit = {}

    var selected = false

    private var hover = false
    private var pressed = false

    override fun update(view: View, manager: StateManager, time: Time, input: Input) {
        super.update(view, manager, time, input)

        if (!enabled) {
            hover = false
            pressed = false

            return
        }

        hover = input.mouse in this

        val lastPressed = pressed

        if (hover && input.buttonDown(Button.LEFT)) {
            pressed = true
        }

        if (!hover || input.buttonUp(Button.LEFT)) {
            pressed = false
        }

        if (pressed && !lastPressed) {
            selected = !selected

            onChange(Event(this, time.copy()))
        }
    }

    override fun render(view: View, renderer: Renderer) {
        renderer.color = when {
            hover -> background.brighter()

            else  -> background
        }

        renderer.fillRoundRect(this, cornerRadius, cornerRadius)

        renderer.color = foreground

        val sizeCheck = font.size
        val space = sizeCheck / 4
        val xCheck = (left + paddingLeft.toDouble()).toInt()
        val yCheck = (top + (height / 2) - sizeCheck / 2).toInt()

        renderer.drawRoundRect(xCheck, yCheck, sizeCheck, sizeCheck, 2, 2)

        renderer.font = font

        renderer.drawString(text, this, xAlign = 0.9)

        renderer.color = accent

        if (selected) {
            renderer.drawLine(xCheck + space, yCheck + space, xCheck + sizeCheck - space, yCheck + sizeCheck - space)
            renderer.drawLine(xCheck + space, yCheck + sizeCheck - space, xCheck + sizeCheck - space, yCheck + space)
        }
    }

    data class Event(
        override val source: CheckBox,
        override val time: Time,
    ) : UIEvent<CheckBox>
}