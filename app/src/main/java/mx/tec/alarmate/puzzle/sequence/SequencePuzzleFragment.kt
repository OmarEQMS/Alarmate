package mx.tec.alarmate.puzzle.sequence

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.VibrationEffect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_alarm_basic.btnSuccess
import kotlinx.android.synthetic.main.fragment_alarm_basic.txtAlarmName
import kotlinx.android.synthetic.main.fragment_alarm_math.txtResult
import kotlinx.android.synthetic.main.fragment_alarm_rewrite.*
import kotlinx.android.synthetic.main.fragment_alarm_sequence.*
import mx.tec.alarmate.R
import mx.tec.alarmate.db.model.Alarm
import mx.tec.alarmate.db.model.Puzzle
import mx.tec.alarmate.puzzle.*
import java.util.*
import kotlin.collections.ArrayList

class SequencePuzzleFragment(override var alarm: Alarm, override var puzzle: Puzzle) : PuzzleFragment(alarm, puzzle) {
    data class DifficultyPolicy(val difficulty: PuzzleDifficulty, val items: ArrayList<Item>, val size: Int)
    data class Item(val resourceId: Int, val elementId: Int)

    var inputSequence: ArrayList<Item> = arrayListOf()
    lateinit var sequence: ArrayList<Item>
    lateinit var policy: DifficultyPolicy
    var viewItems: ArrayList<Int> = arrayListOf(R.id.img1,R.id.img2,R.id.img3,R.id.img4,R.id.img5,R.id.img6)
    var animating = false
    var animationIndex = 0
    var animationHandler = Handler()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alarm_sequence, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtAlarmName.setText(alarm.name)
        btnSuccess.setOnClickListener {
            if(isValid()){
                stopAlarm()
                listener?.onPuzzleSuccess()
            } else {
                reset()
                Toast.makeText(context!!, "Inténtalo de nuevo", Toast.LENGTH_LONG).show()
            }
        }

        imgHeart.setOnClickListener {
            addItemToSequence(Item(R.drawable.like, R.id.imgHeart))
        }
        imgUmbrella.setOnClickListener {
            addItemToSequence(Item(R.drawable.umbrella, R.id.imgUmbrella))
        }
        imgStar.setOnClickListener {
            addItemToSequence(Item(R.drawable.star, R.id.imgStar))
        }
        imgDrop.setOnClickListener {
            addItemToSequence(Item(R.drawable.drop, R.id.imgDrop))
        }
        imgMountain.setOnClickListener {
            addItemToSequence(Item(R.drawable.mountain, R.id.imgMountain))
        }
        imgCamera.setOnClickListener {
            addItemToSequence(Item(R.drawable.camera, R.id.imgCamera))
        }
        imgTv.setOnClickListener {
            addItemToSequence(Item(R.drawable.television, R.id.imgTv))
        }
        imgRobot.setOnClickListener {
            addItemToSequence(Item(R.drawable.robot, R.id.imgRobot))
        }
    }

    override fun startAlarm() {
        reset()
        animateSequence()
        super.startAlarm()
    }

    override fun stopAlarm() {
        super.stopAlarm()
        animating = false
    }

    fun generateSequence(): ArrayList<Item>{
        when(puzzle.difficulty){
            PuzzleDifficulty.EASY -> {
                policy = EASY_POLICY
            }
            PuzzleDifficulty.MEDIUM -> {
                policy = MEDIUM_POLICY
            }
            PuzzleDifficulty.HARD -> {
                policy = HARD_POLICY
            }
            else -> {
                policy = EASY_POLICY
            }
        }

        val sequence = arrayListOf<Item>()
        val r = Random()
        for(i in 0 until policy.size){
            sequence.add(policy.items[r.nextInt(policy.items.size)])
        }
        return sequence
    }

    fun reset(){
        sequence = generateSequence()
        imgPreview.visibility = View.INVISIBLE
        img1.visibility = View.INVISIBLE
        img2.visibility = View.INVISIBLE
        img3.visibility = View.INVISIBLE
        img4.visibility = View.INVISIBLE
        img5.visibility = View.INVISIBLE
        img6.visibility = View.INVISIBLE
        inputSequence.clear()
        animationIndex = 0
    }

    fun animateSequence(){
        if(animating){
            return
        }
        animating = true
        animationHandler.postDelayed(object : Runnable {
            override fun run() {

                if (animating) {
                    animationHandler.postDelayed(this, ANIMATING_INTERVAL_TIME)
                } else {
                    return
                }
                if(animationIndex < sequence.size){
                    imgPreview.visibility = View.VISIBLE
                    imgPreview.setImageResource(sequence[animationIndex].resourceId)
                    animationIndex++
                }else{
                    imgPreview.visibility = View.INVISIBLE
                }
            }
        }, ANIMATING_INTERVAL_TIME)
    }

    fun addItemToSequence(item: Item){
        inputSequence.add(item)
        displayInputSequence()
    }

    fun displayInputSequence(){
        if(inputSequence.size == MAX_ITEMS || inputSequence.size == policy.size){
            if(isValid()){
                stopAlarm()
                listener?.onPuzzleSuccess()
            } else {
                reset()
                Toast.makeText(context!!, "Inténtalo de nuevo", Toast.LENGTH_LONG).show()
            }
        }

        for(i in 0 until if (inputSequence.size > MAX_ITEMS) MAX_ITEMS else inputSequence.size){
            val imgView = view?.findViewById<ImageView>(viewItems[i])
            imgView!!.visibility = View.VISIBLE
            imgView.setImageResource(inputSequence[i].resourceId)
        }
    }

    fun isValid(): Boolean{
        if(inputSequence.size != sequence.size){
            return false
        }
        for(i in 0 until sequence.size){
            if(inputSequence[i] != sequence[i]){
                return false
            }
        }
        return true
    }

    companion object{
        val EASY_POLICY = DifficultyPolicy(
            PuzzleDifficulty.EASY,
            arrayListOf(
                Item(R.drawable.like, R.id.imgHeart),
                Item(R.drawable.umbrella, R.id.imgUmbrella),
                Item(R.drawable.star, R.id.imgStar),
                Item(R.drawable.drop, R.id.imgDrop)
            ),
            4
        )
        val MEDIUM_POLICY = DifficultyPolicy(
            PuzzleDifficulty.MEDIUM,
            arrayListOf(
                Item(R.drawable.like, R.id.imgHeart),
                Item(R.drawable.umbrella, R.id.imgUmbrella),
                Item(R.drawable.star, R.id.imgStar),
                Item(R.drawable.drop, R.id.imgDrop),
                Item(R.drawable.mountain, R.id.imgMountain),
                Item(R.drawable.television, R.id.imgTv)
            ),
            5
        )
        val HARD_POLICY = DifficultyPolicy(
            PuzzleDifficulty.HARD,
            arrayListOf(
                Item(R.drawable.like, R.id.imgHeart),
                Item(R.drawable.umbrella, R.id.imgUmbrella),
                Item(R.drawable.star, R.id.imgStar),
                Item(R.drawable.drop, R.id.imgDrop),
                Item(R.drawable.mountain, R.id.imgMountain),
                Item(R.drawable.television, R.id.imgTv),
                Item(R.drawable.camera, R.id.imgCamera),
                Item(R.drawable.robot, R.id.imgRobot)
            ),
            6
        )

        val MAX_ITEMS = 6
    }
}
