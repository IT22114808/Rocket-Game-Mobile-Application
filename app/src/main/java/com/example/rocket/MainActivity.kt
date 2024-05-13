package com.example.rocket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity(), GameTask {

    private lateinit var rootLayout: LinearLayout
    private lateinit var startBtn: Button
    private lateinit var mGameView: GameView
    private lateinit var scoreTextView: TextView
    private lateinit var highScoreTextView: TextView // Add this

    private var highScore = 0 // Track high score

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startBtn = findViewById(R.id.startBtn)
        rootLayout = findViewById(R.id.rootLayout)
        scoreTextView = findViewById(R.id.score)
        highScoreTextView = findViewById(R.id.highScore) // Initialize high score TextView
        mGameView = GameView(this, this)

        startBtn.setOnClickListener {
            mGameView.setBackgroundResource(R.drawable.bckkground)
            mGameView.resetGame()
            rootLayout.addView(mGameView)
            startBtn.visibility = View.GONE
            scoreTextView.visibility = View.GONE
            highScoreTextView.visibility = View.GONE // Hide high score TextView when game starts
        }
    }

    override fun closeGame(mScore: Int) {
        scoreTextView.text = "Score : $mScore"
        if (mScore > highScore) {
            highScore = mScore // Update high score if necessary
            highScoreTextView.text = "High Score : $highScore" // Update high score TextView
        }
        rootLayout.removeView(mGameView)
        startBtn.visibility = View.VISIBLE
        scoreTextView.visibility = View.VISIBLE
        highScoreTextView.visibility = View.VISIBLE // Show high score TextView when game ends
    }

}