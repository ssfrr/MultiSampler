s.boot

// install quark
Quarks.install("/Users/srussell/Dropbox/projects/active/MultiSampler")

// run tests
(
f = PathName("/Users/srussell/Documents/REAPER Media/Loop Making/The Bad Plus - Flim/renders/");
// check we can construct using a PathName
m = MultiSampler(s, f.folders[2]);
// check we can construct using a path string
//m = MultiSampler(folder.folders[3].fullPath);
)

f.folders[3]
m.play

b = Buffer.read(s, f.folders[3].files[0].fullPath);
b.play
b.bufnum
f.folders[3].files[0]