package main

import (
	"math"
	"math/rand"
	"time"
)

func createMonks(size int) chan int {
	energy := make(chan int, size)
	random := rand.New(rand.NewSource(time.Now().Unix()))

	for i := 0; i < size; i++ {
		currMonk := random.Intn(100) + 1
		energy <- currMonk
		print(currMonk, " ")
	}
	println()
	return energy
}

func fight(energyRead chan int, energyWrite chan int) {
	monk1 := <-energyRead
	monk2 := <-energyRead

	if monk1 > monk2 {
		println("Are fighting:", monk1, "x", monk2, ";", monk1, "won")
		energyWrite <- monk1
	} else {
		println("Are fighting:", monk1, "x", monk2, ";", monk2, "won")
		energyWrite <- monk2
	}
}

func main() {
	const size = 16
	energy := createMonks(size)
	rounds := int(math.Log2(size))
	fights := size / 2

	for i := 1; i <= rounds; i++ {
		nextMonk := make(chan int, fights)
		if i != rounds {
			for j := 0; j < fights; j++ {
				go fight(energy, nextMonk)
			}
			fights /= 2
			energy = nextMonk
		} else {
			go fight(energy, nextMonk)
			winner := <-nextMonk
			println("\nWinner is", winner)
		}
	}
}
