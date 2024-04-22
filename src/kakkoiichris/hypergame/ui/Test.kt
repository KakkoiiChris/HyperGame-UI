package kakkoiichris.hypergame.ui

import kakkoiichris.hypergame.input.Input
import kakkoiichris.hypergame.input.Key
import kakkoiichris.hypergame.state.StateManager
import kakkoiichris.hypergame.util.Time
import kakkoiichris.hypergame.view.Display
import kakkoiichris.hypergame.view.View
import java.awt.Font

class Test(view: View) : UIState(view) {
    init {
        layout = Layout.HardGrid(3u, 3u)

        add(createModule("Dokibird", 10u))
        add(createModule("Mint Fantôme", 15u))
        add(createModule("Quinn Benet", 20u))
        add(createModule("K9 Kuro", 25u))
        add(createModule("Matara Kan", 30u))
        add(createModule("Michi Mochievee", 35u))
        add(createModule("Sayu Sincronisity", 40u))
        add(createModule("Unnämed", 45u))
        add(createModule("Ksononair", 50u))
    }

    private var n = 0

    private fun createModule(text: String, margin: UInt) =
        Label(text).apply {
            this.margin = margin
            id = "button_${n++}"
            font = Font("Times New Roman", Font.PLAIN, 20)
            //onChange = { (source, _) -> println("<${source.text}>") }
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