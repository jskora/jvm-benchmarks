package jskora.jvm;

//import net.nicoulaj.jmh.profilers.YourkitProfiler;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.profile.GCProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

public class StringBenchmarks {

    @State(Scope.Benchmark)
    public static class testState {
        String string1;
        String string2;
        String string3;

        @Setup(Level.Invocation)
        public void init() {
            string1 = "apple";
            string2 = "banana";
            string3 = null;
        }
    }

    @Benchmark
    public void testPlus(Blackhole bh, testState state) {
        bh.consume(state.string3 = state.string1 + state.string2);
    }

    @Benchmark
    public void testConcat(Blackhole bh, testState state) {
        state.string3 = "".concat(state.string1);
        bh.consume(state.string3.concat(state.string2));
    }

    @Benchmark
    public void testFormat(Blackhole bh, testState state) {
        bh.consume(String.format("%s%s", state.string1, state.string2));
    }

    @Benchmark
    public void testBuilder(Blackhole bh, testState state) {
        //noinspection StringBufferReplaceableByString
        bh.consume(new StringBuilder().append(state.string1).append(state.string2).toString());
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(StringBenchmarks.class.getSimpleName())
//                .addProfiler(YourkitProfiler.class)
                .addProfiler(GCProfiler.class)
                .shouldDoGC(true)
                .warmupTime(TimeValue.seconds(5))
                .warmupIterations(5)
                .warmupForks(1)
                .forks(1)
                .mode(Mode.Throughput)
                .measurementTime(TimeValue.seconds(5))
                .measurementIterations(10)
                .build();
        new Runner(opt).run();
    }
}
