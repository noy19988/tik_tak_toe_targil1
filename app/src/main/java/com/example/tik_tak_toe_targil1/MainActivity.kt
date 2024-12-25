package com.example.tik_tak_toe_targil1

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var currentPlayer = "X"
    private val board = Array(3) { arrayOfNulls<String>(3) }
    private var gameOver = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttons = arrayOf(
            findViewById<Button>(R.id.btn1), findViewById<Button>(R.id.btn2), findViewById<Button>(R.id.btn3),
            findViewById<Button>(R.id.btn4), findViewById<Button>(R.id.btn5), findViewById<Button>(R.id.btn6),
            findViewById<Button>(R.id.btn7), findViewById<Button>(R.id.btn8), findViewById<Button>(R.id.btn9)
        )
        val tvResult = findViewById<TextView>(R.id.tvResult)
        val btnPlayAgain = findViewById<Button>(R.id.btnPlayAgain)

        for ((index, button) in buttons.withIndex()) {
            button.setOnClickListener {
                if (!gameOver && button.text.isEmpty()) {
                    val row = index / 3
                    val col = index % 3
                    board[row][col] = currentPlayer
                    button.text = currentPlayer
                    if (checkWinner()) {
                        tvResult.text = "Player $currentPlayer Wins!"
                        gameOver = true
                        btnPlayAgain.visibility = View.VISIBLE
                    } else if (isBoardFull()) {
                        tvResult.text = "It's a Draw!"
                        gameOver = true
                        btnPlayAgain.visibility = View.VISIBLE
                    } else {
                        currentPlayer = if (currentPlayer == "X") "O" else "X"
                    }
                }
            }
        }

        btnPlayAgain.setOnClickListener {
            resetGame(buttons, tvResult)
        }
    }

    private fun checkWinner(): Boolean {
        for (i in 0..2) {
            // Check rows and columns
            if ((board[i][0] == currentPlayer && board[i][1] == currentPlayer && board[i][2] == currentPlayer) ||
                (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i] == currentPlayer)
            ) {
                return true
            }
        }
        // Check diagonals
        if ((board[0][0] == currentPlayer && board[1][1] == currentPlayer && board[2][2] == currentPlayer) ||
            (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0] == currentPlayer)
        ) {
            return true
        }
        return false
    }

    private fun isBoardFull(): Boolean {
        for (row in board) {
            if (row.any { it == null }) {
                return false
            }
        }
        return true
    }

    private fun resetGame(buttons: Array<Button>, tvResult: TextView) {
        for (button in buttons) {
            button.text = ""
        }
        for (i in board.indices) {
            for (j in board[i].indices) {
                board[i][j] = null
            }
        }
        currentPlayer = "X"
        gameOver = false
        tvResult.text = "Tic Tac Toe"
        findViewById<Button>(R.id.btnPlayAgain).visibility = View.GONE
    }
}