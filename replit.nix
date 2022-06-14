{ pkgs }: with pkgs; {
    deps = [
      maven
      clojure
      clojure-lsp
      babashka
      clj-kondo
    ];
}