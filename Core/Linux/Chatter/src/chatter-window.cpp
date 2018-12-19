
#include "chatter-window.h"

ChatterWindow::ChatterWindow()
	: Glib::ObjectBase("ChatterWindow")
	, Gtk::Window()
	, headerbar(nullptr)
	, label(nullptr)
{
	builder = Gtk::Builder::create_from_resource("/org/gnome/Chatter/chatter-window.ui");
	builder->get_widget("headerbar", headerbar);
	builder->get_widget("label", label);
	add(*label);
	label->show();
	set_titlebar(*headerbar);
	headerbar->show();
}
