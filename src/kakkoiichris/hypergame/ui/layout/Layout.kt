package kakkoiichris.hypergame.ui.layout

import kakkoiichris.hypergame.ui.Module
import kotlin.math.min

sealed interface Layout {
    operator fun invoke(parent: Panel, children: List<Module>)

    data object None : Layout {
        override fun invoke(parent: Panel, children: List<Module>) = Unit
    }

    data object Fixed : Layout {
        override operator fun invoke(parent: Panel, children: List<Module>) {
            for (component in children) {
                var space = (parent.paddingTop + component.marginTop).toInt()

                if (component.top - parent.top < space) {
                    component.top = parent.top + space
                }

                space = (parent.paddingRight + component.marginRight).toInt()

                if (parent.right - component.right < space) {
                    component.right = parent.right - space
                }

                space = (parent.paddingBottom + component.marginBottom).toInt()

                if (parent.bottom - component.bottom < space) {
                    component.bottom = parent.bottom - space
                }

                space = (parent.paddingLeft + component.marginLeft).toInt()

                if (component.left - parent.left < space) {
                    component.left = parent.left + space
                }
            }
        }
    }

    class HardGrid(val rows: UInt, val cols: UInt) : Layout {
        override operator fun invoke(parent: Panel, children: List<Module>) {
            val usableWidth = parent.width - (parent.paddingLeft + parent.paddingRight).toInt()
            val usableHeight = parent.height - (parent.paddingTop + parent.paddingBottom).toInt()

            val ox = parent.x + parent.paddingLeft.toInt()
            val oy = parent.y + parent.paddingTop.toInt()

            val gridWidth = usableWidth / cols.toInt()
            val gridHeight = usableHeight / rows.toInt()

            var i = 0

            for (r in 0 until rows.toInt()) {
                for (c in 0 until cols.toInt()) {
                    if (i !in children.indices) {
                        return
                    }

                    val component = children[i++]

                    val x = ox + c * gridWidth + component.marginLeft.toInt()
                    val y = oy + r * gridHeight + component.marginTop.toInt()
                    val width = gridWidth - (component.marginLeft + component.marginRight).toInt()
                    val height = gridHeight - (component.marginTop + component.marginBottom).toInt()

                    component.setBounds(x, y, width, height)
                }
            }
        }
    }

    class Border : Layout {
        override fun invoke(parent: Panel, children: List<Module>) {
            val top = children[Region.TOP.ordinal]
            val right = children[Region.RIGHT.ordinal]
            val bottom = children[Region.BOTTOM.ordinal]
            val left = children[Region.LEFT.ordinal]
            val center = children[Region.CENTER.ordinal]

            val centerMaxWidth = min(center.width, parent.width)
            val centerMaxHeight = min(center.height, parent.height)

            val verticalMaxWidth = parent.width
            val verticalMaxHeight = (parent.height - centerMaxHeight) / 2

            val horizontalMaxWidth = (parent.width - centerMaxWidth) / 2
            val horizontalMaxHeight = centerMaxHeight

            //val centerWidth = centerMaxWidth - (center.marginHorizontal + parent.paddingHorizontal).toInt()
            //val centerHeight = centerMaxHeight - (center.marginVertical + parent.paddingVertical).toInt()

            top.setBounds(0.0, 0.0, verticalMaxWidth, verticalMaxHeight)
            bottom.setBounds(0.0, parent.height - verticalMaxHeight, verticalMaxWidth, verticalMaxHeight)
            left.setBounds(0.0, verticalMaxHeight, horizontalMaxWidth, horizontalMaxHeight)
            right.setBounds(
                parent.width - horizontalMaxWidth,
                verticalMaxHeight,
                horizontalMaxWidth,
                horizontalMaxHeight
            )
            center.setBounds(horizontalMaxWidth, verticalMaxHeight, centerMaxWidth, centerMaxHeight)
        }

        enum class Region {
            TOP,
            RIGHT,
            BOTTOM,
            LEFT,
            CENTER,
        }
    }
}