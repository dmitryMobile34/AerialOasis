package com.appxplore.clawstar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_gameplay.*

class Gameplay : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gameplay)

        val cells = arrayListOf(cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8, cell9)

        var isXTurn = true

        for (cell in cells){
            cell.setOnClickListener {
                rulestxt.isVisible = false
                if (isXTurn) {
                    cell.setImageResource(R.drawable.cancel)
                    cell.tag = "X"
                } else {
                    cell.setImageResource(R.drawable.circumference)
                    cell.tag = "O"
                }
                cell.isClickable = false
                cells.remove(cell)
                var win = checkWinner()
                if (!win && cells.isNullOrEmpty()) {
                    // draw Action
                    endGameText.isVisible = true
                    endGameText.text = "Draw!"
                    restartBtn.isVisible = true
                }
                if (win) {
                    if (isXTurn) {
                        // X Win Action
                        endGameText.isVisible = true
                        endGameText.text = "First-player wins!"
                    } else {
                        // O Win Action
                        endGameText.isVisible = true
                        endGameText.text = "Second-player wins!"
                    }
                    for (i in cells) {
                        i.isClickable = false
                    }
                    restartBtn.isVisible = true
                }
                isXTurn = !isXTurn
            }
        }

    }

    private fun checkWinner(): Boolean {
        val cellsWinnable = arrayListOf(
            arrayListOf(cell1, cell2, cell3), arrayListOf(cell4, cell5, cell6), arrayListOf(cell7, cell8, cell9),
            arrayListOf(cell1, cell4, cell7), arrayListOf(cell2, cell5, cell8), arrayListOf(cell3, cell6, cell9),
            arrayListOf(cell1, cell5, cell9), arrayListOf(cell3, cell5, cell7)
        )
        for (winComb in cellsWinnable) {
            if ((winComb[0].tag == "X" && winComb[1].tag == "X" && winComb[2].tag == "X") || (winComb[0].tag == "O" && winComb[1].tag == "O" && winComb[2].tag == "O")) {
                return true
            }
        }
        return false
    }

    fun gamerestart(view: View) {
        Intent(applicationContext, Gameplay::class.java).also { startActivity(it) }
        finish()
    }

}