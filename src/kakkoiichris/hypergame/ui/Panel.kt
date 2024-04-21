package kakkoiichris.hypergame.ui

import kakkoiichris.hypergame.input.Input
import kakkoiichris.hypergame.state.StateManager
import kakkoiichris.hypergame.util.Time
import kakkoiichris.hypergame.view.View

open class Panel : Module() {
    var layout: Layout = Layout.None

    override fun update(view: View, manager: StateManager, time: Time, input: Input) {
        layout(this, children)

        super.update(view, manager, time, input)
    }
}