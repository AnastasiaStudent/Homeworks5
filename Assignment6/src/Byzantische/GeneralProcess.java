package Byzantische;

import Byzantische.Interface.Broadcaster;
import Byzantische.Interface.PathRepository;
import Byzantische.Interface.ValueStrategy;

public class GeneralProcess extends Process {

    /**
     * @param id
     * @param configuration
     * @param repository
     * @param broadcaster
     * @param strategy
     */
    public GeneralProcess(int id, Configuration configuration, PathRepository repository, Broadcaster broadcaster, ValueStrategy strategy) {
        super(id, configuration, repository, broadcaster, strategy);
        nodes.put("", strategy.getSourceValue());
    }


    @Override
    public Value decide() {
        //
        // The source process doesn't have to do all the work - since it's the decider,
        // it simply looks at its input value to pick the appropriate decision value.
        //
        return nodes.get("").input;
    }
}

