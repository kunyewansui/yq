/**
 * Created by gustinlau on 04/11/2017.
 */
$(function () {
    var navLinks = nav.find("a");
    if ($("#a_nav").find('li.active').length === 0 && navLinks.length > 0) {
        navLinks[0].click();
    }
});
