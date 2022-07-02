async function test2(){
    setTimeout(()=>{
        console.log("2번끝");
    },3000)
    
}

async function test1(){
    await test2();
    console.log("2번뒤에 나오는가?");
    return test2();
}

test1();