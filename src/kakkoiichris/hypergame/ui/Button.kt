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

    var eventListener: (Event) -> Unit = {}

    private var hover = false
    private var pressed = false

    override fun update(view: View, manager: StateManager, time: Time, input: Input) {
        super.update(view, manager, time, input)

        hover = input.mouse in this

        val lastPressed = pressed

        pressed = hover && input.buttonHeld(Button.LEFT)

        if (pressed && !lastPressed) {
            eventListener(Event(this, time.copy()))
        }
    }

    override fun render(view: View, renderer: Renderer) {
        renderer.color = when {
            pressed -> accent.darker()

            hover   -> accent

            else    -> background
        }

        renderer.fill(rectangle)

        renderer.color = foreground

        renderer.draw(rectangle)

        renderer.font = font

        renderer.drawString(text, this)
    }
}