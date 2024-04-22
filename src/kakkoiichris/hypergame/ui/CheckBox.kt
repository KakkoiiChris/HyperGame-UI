package kakkoiichris.hypergame.ui

import kakkoiichris.hypergame.input.Button
import kakkoiichris.hypergame.input.Input
import kakkoiichris.hypergame.media.Renderer
import kakkoiichris.hypergame.state.StateManager
import kakkoiichris.hypergame.util.Time
import kakkoiichris.hypergame.view.View
import java.awt.Font

open class CheckBox(var text: String = "") : Module() {
    var font = Font("Monospaced", Font.PLAIN, 15)

    var eventListener: (Event) -> Unit = {}

    var selected = false

    private var hover = false
    private var pressed = false

    override fun update(view: View, manager: StateManager, time: Time, input: Input) {
        super.update(view, manager, time, input)

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

            eventListener(Event(this, time.copy()))
        }
    }

    override fun render(view: View, renderer: Renderer) {
        renderer.color = background

        renderer.fillRect(this)

        renderer.color = foreground

        val sizeCheck = font.size
        val xCheck = (left + paddingLeft.toDouble()).toInt()
        val yCheck = (top + (height / 2) - sizeCheck / 2).toInt()

        renderer.drawRect(xCheck, yCheck, sizeCheck, sizeCheck)

        if (selected) {
            renderer.drawLine(xCheck, yCheck, xCheck + sizeCheck, yCheck + sizeCheck)
            renderer.drawLine(xCheck, yCheck + sizeCheck, xCheck + sizeCheck, yCheck)
        }

        renderer.font = font

        renderer.drawString(text, this, xAlign = 0.9)
    }

    data class Event(
        override val source: CheckBox,
        override val time: Time,
    ) : UIEvent<CheckBox>
}