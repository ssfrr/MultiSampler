/*
MultiSampler is a class to load a folder full of samples. Each time the sampler is triggered it will randomly select from the collection of samples. This is useful e.g. if you have a folder of similar but slightly different snare samples and want to easily trigger them, adding variation to your sample playback.
*/

MultiSampler {
    var <bufs, lastPlayed;
    *new {
        | samplePath, server |
        var path = if(samplePath.class == PathName, samplePath, { PathName(samplePath) });
        var bufs = [];
        server.isNil.if { server = Server.default };

        path.files.do {
            | file |
            // WARNING: we're not waiting for this to be confirmed from the server, so the buffers
            // won't be available immediately for playback
            var isAudio = ["wav", "ogg", "mp3"].any { | ext |
                file.extension == ext;
            };
            isAudio.if {
                bufs = bufs.add(Buffer.read(server, file.fullPath));
            };
        }
        ^super.newCopyArgs(bufs, -1);
    }

    // return a random buffer in the collection
    getBuf {
        var n = this.bufs.size;
        var idx = if(n > 2, n.xrand(lastPlayed), n.rand);
        lastPlayed = idx;
        ^bufs[idx];
    }

    play {
        | velocity=1 |
        this.getBuf.play(mul: velocity);
    }
}
