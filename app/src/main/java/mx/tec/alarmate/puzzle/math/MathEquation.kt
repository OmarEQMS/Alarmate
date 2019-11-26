package mx.tec.alarmate.puzzle.math

import mx.tec.alarmate.puzzle.PuzzleDifficulty
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

data class DifficultyPolicy(val difficulty: PuzzleDifficulty, val validOperands: ArrayList<Operand>, val numberOfLiteralsRange: Pair<Int, Int>, val literalsRange: Pair<Int, Int>)

class MathEquation(val difficulty: PuzzleDifficulty){
    var literals: ArrayList<Int>
    var operands: ArrayList<Operand>
    var result: Int

    companion object{
        val EASY_POLICY = DifficultyPolicy(
            PuzzleDifficulty.EASY,
            arrayListOf(Operand.PLUS, Operand.MINUS),
            Pair(2,2),
            Pair(5,20)
        )
        val MEDIUM_POLICY = DifficultyPolicy(
            PuzzleDifficulty.MEDIUM,
            arrayListOf(Operand.PLUS, Operand.MINUS, Operand.MULTIPLY),
            Pair(2,3),
            Pair(5,40)
        )
        val HARD_POLICY = DifficultyPolicy(
            PuzzleDifficulty.HARD,
            arrayListOf(Operand.PLUS, Operand.MINUS, Operand.MULTIPLY, Operand.DIVIDE),
            Pair(3,4),
            Pair(-20,20)
        )
    }

    init{
        literals = arrayListOf()
        operands = arrayListOf()
        val r = Random()
        val policy: DifficultyPolicy

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