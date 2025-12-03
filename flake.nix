{
  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs/nixpkgs-unstable";
  };
  outputs =
    inputs@{
      flake-parts,
      systems,
      ...
    }:
    flake-parts.lib.mkFlake { inherit inputs; } {
      systems = import systems;
      perSystem =
        { pkgs, ... }:
        {
          formatter = pkgs.alejandra;

          devShells.default = pkgs.mkShell {
            buildInputs = with pkgs; [
              (jdk23.override { enableJavaFX = true; })

              gtk3
              glib
              jdt-language-server
              gdk-pixbuf
              pango
              cairo
              at-spi2-atk
              atk
              mesa
              libxkbcommon
              xorg.libX11
              xorg.libXext
              xorg.libXcursor
              xorg.libXi
              xorg.libXrandr
              pipewire
              xdg-utils
              libsecret
              gnome-keyring
            ];
          };
        };
    };
}
