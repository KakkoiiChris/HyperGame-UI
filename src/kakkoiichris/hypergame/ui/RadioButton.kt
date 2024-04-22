package kakkoiichris.hypergame.ui

import kakkoiichris.hypergame.input.Button
import kakkoiichris.hypergame.input.Input
import kakkoiichris.hypergame.media.Renderer
import kakkoiichris.hypergame.state.StateManager
import kakkoiichris.hypergame.util.Time
import kakkoiichris.hypergame.view.View
import java.awt.Font

open class RadioButton private constructor(var text: String, private val group: Group) : Module() {
    var font = Font("Monospaced", Font.PLAIN, 15)

    var eventListener: (Event) -> Unit = {}

    var selected = false

    private var hover = false
    private var pressed = false

    private fun toggle(selected: Boolean, time: Time) {
        this.selected = selected

        eventListener(Event(this, time.copy()))
    }

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
            selected = true

            group.toggle(this, time)
        }
    }

    override fun render(view: View, renderer: Renderer) {
        renderer.color = when {
            pressed -> accent.darker()

            hover   -> accent

            else    -> background
        }

        renderer.fillRect(this)

        renderer.color = foreground

        val sizeDot = font.size
        val xDot = (left + paddingLeft.toDouble()).toInt()
        val yDot = (top + (height / 2) - sizeDot / 2).toInt()

        renderer.drawOval(xDot, yDot, sizeDot, sizeDot)

        if (selected) {
            renderer.fillOval(xDot + sizeDot / 4, yDot + sizeDot / 4, sizeDot / 2, sizeDot / 2)
        }

        renderer.font = font

        renderer.drawString(text, this, xAlign = 0.9)
    }

    data class Event(
        override val source: RadioButton,
        override val time: Time,
    ) : UIEvent<RadioButton>

    class Group {
        private val members = mutableListOf<RadioButton>()

        fun create(text: String): RadioButton {
            val button = RadioButton(text, this)

            if (members.isEmpty()) button.selected = true

            members += button

            return button
        }

        internal fun toggle(button: RadioButton, time: Time) {
            for (member in members) {
                if (member !== button) {
                    member.toggle(false, time)
                }
            }

            button.toggle(true, time)
        }
    }
}