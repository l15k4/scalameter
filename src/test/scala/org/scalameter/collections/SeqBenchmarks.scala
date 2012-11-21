package org.scalameter
package collections



import collection._
import Key._



class SeqBenchmarks extends PerformanceTest.Regression with Collections {

  def persistor = new persistence.SerializationPersistor()

  /* tests */

  performance of "Seq" in {

    measure method "apply" config (
      exec.benchRuns -> 36,
      exec.independentSamples -> 9,
      reports.regression.significance -> 1e-13
    ) in {
      val from = 1000000
      val to = 5000000
      val by = 1000000
      var sideeffect = 0

      using(arrays(from, to, by)) curve("Array") in { xs =>
        var i = 0
        var sum = 0
        val len = xs.length
        val until = len * 3
        while (i < until) {
          sum += xs.apply(i % len)
          i += 1
        }
        sideeffect = sum
      }

      using(arraybuffers(from, to, by)) curve("ArrayBuffer") in { xs =>
        var i = 0
        var sum = 0
        val len = xs.length
        val until = len * 3
        while (i < until) {
          sum += xs.apply(i % len)
          i += 1
        }
        sideeffect = sum
      }

      using(vectors(from, to, by)) curve("Vector") in { xs =>
        var i = 0
        var sum = 0
        val len = xs.length
        val until = len * 3
        while (i < until) {
          sum += xs.apply(i % len)
          i += 1
        }
        sideeffect = sum
      }

      using(ranges(from, to, by)) curve("Range") in { xs =>
        var i = 0
        var sum = 0
        val len = xs.length
        val until = len * 3
        while (i < until) {
          sum += xs.apply(i % len)
          i += 1
        }
        sideeffect = sum
      }

    }

    measure method "update" config (
      exec.benchRuns -> 36,
      exec.independentSamples -> 9,
      reports.regression.significance -> 1e-13
    ) in {
      val from = 1000000
      val to = 5000000
      val by = 1000000
      var sideeffect = 0

      using(arrays(from, to, by)) curve("Array") in { xs =>
        var i = 0
        var sum = 0
        val len = xs.length
        val until = len * 3
        while (i < until) {
          xs.update(i % len, i)
          i += 1
        }
        sideeffect = sum
      }

      using(arraybuffers(from, to, by)) curve("ArrayBuffer") in { xs =>
        var i = 0
        var sum = 0
        val len = xs.length
        val until = len * 3
        while (i < until) {
          xs.update(i % len, i)
          i += 1
        }
        sideeffect = sum
      }

    }

    measure method "append" config (
      exec.benchRuns -> 36,
      exec.independentSamples -> 9,
      reports.regression.significance -> 1e-13
    ) in {
      val from = 200000
      val to = 2200000
      val by = 400000

      using(sizes(from, to, by)) curve("Vector") config (
        exec.benchRuns -> 32,
        exec.independentSamples -> 4,
        exec.outliers.suspectPercent -> 66,
        exec.outliers.covMultiplier -> 1.4,
        exec.noise.magnitude -> 1.0
      ) in { len =>
        var i = 0
        var vector = Vector.empty[Int]
        while (i < len) {
          vector = vector :+ i
          i += 1
        }
      }

    }

    measure method "prepend" config (
      exec.benchRuns -> 36,
      exec.independentSamples -> 9,
      reports.regression.significance -> 1e-13
    ) in {
      val from = 200000
      val to = 2200000
      val by = 400000

      using(sizes(from, to, by)) curve("Vector") config (
        exec.benchRuns -> 32,
        exec.independentSamples -> 4,
        exec.outliers.suspectPercent -> 66,
        exec.outliers.covMultiplier -> 1.4,
        exec.noise.magnitude -> 1.0
      ) in { len =>
        var i = 0
        var vector = Vector.empty[Int]
        while (i < len) {
          vector = i +: vector
          i += 1
        }
      }

      using(sizes(from, to, by)) curve("List") in { len =>
        var i = 0
        var list = List.empty[Int]
        while (i < len) {
          list = i :: list
          i += 1
        }
      }

    }

    measure method "sorted" config (
      exec.benchRuns -> 36,
      exec.independentSamples -> 9,
      reports.regression.significance -> 1e-13
    ) in {
      val from = 800000
      val to = 4000000
      val by = 600000

      using(arrays(from, to, by)) curve("Array") in {
        _.sorted
      }

      using(vectors(from, to, by)) curve("Vector") in {
        _.sorted
      }

      using(lists(from, to, by)) curve("List") config (
        exec.benchRuns -> 32,
        exec.independentSamples -> 4,
        exec.outliers.suspectPercent -> 50,
        exec.outliers.covMultiplier -> 1.6,
        exec.noise.magnitude -> 1.0
      ) in {
        _.sorted
      }

    }

  }

}

















