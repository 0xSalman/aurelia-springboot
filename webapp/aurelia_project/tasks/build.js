import gulp from 'gulp';
import transpile from './transpile';
import processMarkup from './process-markup';
import processCSS from './process-css';
import {build} from 'aurelia-cli';
import project from '../aurelia.json';

// FIXME https://github.com/aurelia/cli/issues/248

export default gulp.series(
  copySplashStyle,
  readProjectConfiguration,
  gulp.parallel(
    transpile,
    processMarkup,
    processCSS
  ),
  writeBundles
);

function copySplashStyle(done) {
  var splashCss = './' + project.paths.root + '/styles/initial.css';
  var dest = './' + project.build.targets[0].output;
  gulp.src(splashCss).pipe(gulp.dest(dest));
  done();
}

function readProjectConfiguration() {
  return build.src(project);
}

function writeBundles() {
  console.log(project.platform);
  return build.dest();
}
