chatter_sources = [
  'main.cpp',
  'chatter-window.cpp',
]

chatter_deps = [
  dependency('gtkmm-3.0', version: '>= 3.18'),
]

gnome = import('gnome')

chatter_sources += gnome.compile_resources('chatter-resources',
  'chatter.gresource.xml',
  c_name: 'chatter'
)

executable('chatter', chatter_sources,
  dependencies: chatter_deps,
  install: true,
)
