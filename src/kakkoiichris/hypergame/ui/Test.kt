package kakkoiichris.hypergame.ui

import kakkoiichris.hypergame.input.Input
import kakkoiichris.hypergame.input.Key
import kakkoiichris.hypergame.state.StateManager
import kakkoiichris.hypergame.ui.form.TextBox
import kakkoiichris.hypergame.ui.layout.Layout
import kakkoiichris.hypergame.util.Time
import kakkoiichris.hypergame.view.Display
import kakkoiichris.hypergame.view.View
import java.awt.Font

class Test(view: View) : UIState(view) {
    init {
        layout = Layout.HardGrid(3u, 3u)

        add(createModule("Dokibird"))
        add(createModule("Mint Fantôme"))
        add(createModule("Quinn Benet"))
        add(createModule("K9 Kuro"))
        add(createModule("Matara Kan"))
        add(createModule("Michi Mochievee"))
        add(createModule("Sayu Sincronisity"))
        add(createModule("Unnämed"))
        add(createModule("Ksononair"))
    }

    private var n = 0

    private fun createModule(text: String) =
        TextBox().apply {
            margin = 10u
            id = "button_${n++}"
            font = Font("Times New Roman", Font.PLAIN, 20)
        }

    override fun swapTo(view: View) {
    }

    override fun swapFrom(view: View) {
    }

    override fun update(view: View, manager: StateManager, time: Time, input: Input) {
        super.update(view, manager, time, input)

        if (input.inWindow && input.keyHeld(Key.SPACE)) {
            width = input.x.toDouble()
            height = input.y.toDouble()
        }

        if (input.keyDown(Key.ESCAPE)) {
            enabled = !enabled
        }

        if (input.keyDown(Key.ENTER)) {
            children.forEach { it.enabled = !it.enabled }
        }
    }

    override fun halt(view: View) {
    }
}

fun main() {
    val display = Display(800, 800, title = "HYPERGAME UI TEST")

    display.manager.push(Test(display))

    display.open()
}