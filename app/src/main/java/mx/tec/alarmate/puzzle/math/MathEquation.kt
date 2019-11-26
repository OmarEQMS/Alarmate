package mx.tec.alarmate.puzzle.math

import mx.tec.alarmate.puzzle.PuzzleDifficulty
import mx.tec.alarmate.puzzle.math.MathPuzzleFragment.Companion.EASY_POLICY
import mx.tec.alarmate.puzzle.math.MathPuzzleFragment.Companion.HARD_POLICY
import mx.tec.alarmate.puzzle.math.MathPuzzleFragment.Companion.MEDIUM_POLICY
import java.util.*
import java.util.Arrays.asList
import java.util.Collections.unmodifiableList
import kotlin.collections.ArrayList


enum class Operand {
    PLUS,
    MINUS,
    MULTIPLY,
    DIVIDE;

    companion object{
        private val SIZE = values().size
        private val RANDOM = Random()

        fun random(): Operand {
            return values()[RANDOM.nextInt(SIZE)]
        }

        fun random(n: Int): ArrayList<Operand>{
            val operands: ArrayList<Operand> = arrayListOf()
            for (i in 0 until n) {
                operands.add(random())
            }
            return operands
        }
    }
}

class MathEquation(val difficulty: PuzzleDifficulty){
    var literals: ArrayList<Int>
    var operands: ArrayList<Operand>
    var result: Int

    init{
        literals = arrayListOf()
        operands = arrayListOf()
        val r = Random()
        val policy: MathPuzzleFragment.DifficultyPolicy

        when(difficulty){
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

        val numberOfLiteralsRangeSize = policy.numberOfLiteralsRange.second - policy.numberOfLiteralsRange.first
        val literalsRangeSize = policy.literalsRange.second - policy.literalsRange.first
        val literalsCount = r.nextInt(numberOfLiteralsRangeSize+1)+policy.numberOfLiteralsRange.first
        for(i in 0 until literalsCount){
            val literal = r.nextInt(literalsRangeSize)+policy.literalsRange.first
            literals.add(if (literal == 0) r.nextInt(policy.literalsRange.second)+1 else literal)
        }
        operands = Operand.random(literalsCount - 1)
        result = evaluate()
    }

    fun validate(result: Int): Boolean{
        return result == this.result
    }

    fun evaluate(): Int{
        var result = literals[0]
        for(i in 0 until operands.size){
            when(operands[i]){
                Operand.PLUS -> result = result + literals[i+1]
                Operand.MINUS -> result = result - literals[i+1]
                Operand.MULTIPLY -> result = result * literals[i+1]
                Operand.DIVIDE -> result = result / literals[i+1]
            }
        }
        return result
    }

    override fun toString(): String {
        var result = literals[0].toString()
        for(i in 0 until operands.size){
            when(operands[i]){
                Operand.PLUS -> result = "${result} + ${literals[i+1]}"
                Operand.MINUS -> result = "${result} - ${literals[i+1]}"
                Operand.MULTIPLY -> result = "${result} * ${literals[i+1]}"
                Operand.DIVIDE -> result = "${result} / ${literals[i+1]}"
            }
        }
        return "${result} ="
    }
}