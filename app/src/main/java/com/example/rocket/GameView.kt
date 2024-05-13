package com.example.rocket

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import java.lang.Exception

class GameView(var c: Context, var gameTask: GameTask) : View(c) {

    private val myPaint = Paint()
    private var speed = 1
    private var time = 0
    private var score = 0
    private var highScore = 0 // Moved highScore here
    private var myRocketPosition = 0
    private val planets = ArrayList<HashMap<String, Any>>()

    var viewWidth = 0
    var viewHeight = 0
    // Reset method to reset the game
    fun resetGame() {
        score = 0
        speed = 1
        time = 0
        myRocketPosition = 0
        planets.clear()
        invalidate() // Redraw the view to reflect the reset state
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        viewWidth = canvas.width
        viewHeight = canvas.height

        if (time % 700 < 10 + speed) {
            val map = HashMap<String, Any>()
            map["lane"] = (0..2).random()
            map["startTime"] = time
            planets.add(map)
        }
        time += 10 + speed
        val rocketWidth = viewWidth / 4
        val rocketHeight = rocketWidth + 10
        myPaint.style = Paint.Style.FILL

        val d = resources.getDrawable(
            R.drawable.brocket,
            null
        ) // Assuming "rocket" is the correct name of your drawable
        d.setBounds(
            myRocketPosition * viewWidth / 3 + viewWidth / 15 + 15,
            viewHeight - 2 - rocketHeight,
            myRocketPosition * viewWidth / 3 + viewWidth / 15 + rocketWidth - 15,
            viewHeight - 2
        )
        d.draw(canvas)

        myPaint.color = Color.GREEN

        for (i in planets.indices) {
            try {
                val rocketX = planets[i]["lane"] as Int * viewWidth / 3 + viewWidth / 15
                val rocketY = time - planets[i]["startTime"] as Int
                val d2 = resources.getDrawable(R.drawable.planet, null)


                d2.setBounds(
                    rocketX + 25, rocketY - rocketHeight, rocketX + rocketWidth - 25, rocketY
                )
                d2.draw(canvas)
                if (planets[i]["lane"] as Int == myRocketPosition) {
                    if (rocketY > viewHeight - 2 - rocketHeight && rocketY < viewHeight - 2) {
                        gameTask.closeGame(score)
                    }
                }
                if (rocketY > viewHeight + rocketHeight) {
                    planets.removeAt(i)
                    score++
                    speed = 1 + Math.abs(score / 10)
                    if (score > highScore) {
                        highScore = score
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        myPaint.color = Color.WHITE
        myPaint.textSize = 60f
        canvas.drawText("Score : $score ", 80f, 80f, myPaint)
        canvas.drawText("Speed : $speed ", 380f, 80f, myPaint)
        canvas.drawText("High Score : $highScore ", 680f, 80f, myPaint) // Display high score

        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                val x1 = event.x
                if (x1 < viewWidth / 2) {
                    if (myRocketPosition > 0) {
                        myRocketPosition--
                    }
                }
                if (x1 > viewWidth / 2) {
                    if (myRocketPosition < 2) {
                        myRocketPosition++
                    }
                }
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
            }
        }
        return true
    }
}