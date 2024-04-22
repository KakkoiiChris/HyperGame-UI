package kakkoiichris.hypergame.ui

import kakkoiichris.hypergame.input.Button
import kakkoiichris.hypergame.input.Input
import kakkoiichris.hypergame.media.Renderer
import kakkoiichris.hypergame.state.StateManager
import kakkoiichris.hypergame.util.Time
import kakkoiichris.hypergame.view.View
import java.awt.Font

open class Button(var text: String = "") : Module() {
    var font = Font("Monospaced", Font.PLAIN, 15)

    var onClick: (Event) -> Unit = {}

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
            onClick(Event(this, time.copy()))
        }
    }

    override fun render(view: View, renderer: Renderer) {
        renderer.color = when {
            pressed -> background.darker()

            hover   -> background.brighter()

            else    -> background
        }

        renderer.fillRoundRect(this, cornerRadius, cornerRadius)

        renderer.color = accent
        renderer.stroke = stroke

        renderer.drawRoundRect(this, cornerRadius, cornerRadius)

        renderer.color = foreground
        renderer.font = font

        renderer.drawString(text, this)
    }

    data class Event(
        override val source: kakkoiichris.hypergame.ui.Button,
        override val time: Time,
    ) : UIEvent<kakkoiichris.hypergame.ui.Button>
}