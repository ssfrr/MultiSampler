/*
MultiSampler is a class to load a folder full of samples. Each time the sampler is triggered it will randomly select from the collection of samples. This is useful e.g. if you have a folder of similar but slightly different snare samples and want to easily trigger them, adding variation to your sample playback.
*/

MultiSampler {
    var <bufs, lastPlayed;
    *new {
        | server, samplePath |
        var path = if(samplePath.class == PathName, samplePath, { PathName(samplePath) });
        var bufs = [];

        path.files.do {
            | file |
            // WARNING: we're not waiting for this to be confirmed from the server, so the buffers
            // won't be available immediately for playback
            // WARNING: there's no handling here for a file that can't be loaded (e.g. not a sound file)
            bufs = bufs.add(Buffer.read(server, file.fullPath));
        }
        ^super.newCopyArgs(bufs, -1);
    }
    play {
        var n = this.bufs.size;
        var idx = if(n > 2, n.xrand(lastPlayed), n.rand);
        "playing idx %\n".postf(idx);
        lastPlayed = idx;
        bufs[idx].play;
    }
}