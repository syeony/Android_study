fun go(doSome: (str:String) -> Unit){
//inline fun go(doSome: (str:String) -> Unit){
    doSome("Hello Inline")
}

fun main(){
    println("before")
    go { str ->
        println(str)
    }
    println("after")
}